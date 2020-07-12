
package GUI;

import Piece.Chair;
import Piece.Setting;
import Piece.User;
import TDA.LCDE;
import TDA.ListIterator;
import java.applet.AudioClip;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Juego{
    private LCDE<Chair> listChairs = new LCDE();
    private LCDE <Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    private LCDE<User> listUsers = new LCDE();
    private LCDE<User> listUsersGame = new LCDE<>();
    private Deque<User> PileUsers = new ArrayDeque<>();
    private Math calcular;                          //hará las operaciones
    //Desde el FXML
    @FXML Button btnPlay= new Button();
    @FXML ImageView ivUser = new ImageView();
    @FXML Pane spPane= new StackPane();
    boolean signo=true;                             //nos permite tomar la parte positiva o negativa de Y en la función.
    double radio=115;                               //radio de la circunferencia a ser dibujada
    double posX=-radio;
    double posY=0;
    boolean parar= false;                           //es el que controla el hilo, al estar en true el hilo de para.
    Image img;
    double pos_X=0;
    double pos_Y=0;
    private Setting sett = new Setting(4);
     

    
    public Juego() throws FileNotFoundException {
    }

    //cuando se presiona el botón stop el hilo se interrumpirá.
    @FXML protected void btnStopClicked(){
        
        parar=true;
    }

    //define que se hará al presionar al botón play
    @FXML protected void btnPlayClicked() {
        
        parar=false;                                //se lo pone en false porque puede que antes se haya dado al botón stop.

        //moverá las pelotas en el tiempo
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    //aqui se pone lo que se quiere ejecutar en el tiempo
                    public void run() {
                        Caminete();    //llama a la función que mueve las figuras
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(50);        //establece cada cuanto tiempo se ejecutará la acción del run
                        synchronized (this) {
                            while (parar) {
                                Thread.interrupted();       //se interrumpe el hilo
                                wait();
                            }
                        }

                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();           //inicia el hilo
    }


    public void initialize(Double nP) throws MalformedURLException {
        sett = new Setting(nP);
        listChairs = sett.addChairs(); //En Seeting crera la lista de sillas
        listUsers = sett.addPlayers();
        for (int i = 0; i < listChairs.size(); i++) { //Guarda cada silla en una pila
            listChairP.push(listChairs.get(i));
        }
          for (int i = 0; i < listUsers.size(); i++) {
            PileUsers.push(listUsers.get(i));
        }
        OrganizeControllerChairs();
        OrganizeControllerUser();
    }
    
    //mueve al objeto a una posición X,Y según la ecuación
    private void Caminete(){
        
        ListIterator<User> it1= listUsersGame.Iterator();
         while(it1.Limit()){
             this.pos_X=it1.previous().getPosX();
             this.pos_Y=it1.previous().getPosY();
             trazarCircunferencia(it1.previous().getImage());
         
         }
        System.out.println(this.listUsersGame.size());
    }
        
    public void trazarCircunferencia(ImageView ivUser){
        ivUser.setTranslateX(pos_X);
        //verifica que se esté recorriendo hacia la derecha en el eje X
        if(signo){
            pos_Y=calcular.sqrt(calcular.abs((pos_X*pos_X)-(radio*radio)));     //ecuación de la circunferencia
            pos_X+=4;                                                         //aumentamos la posición X
            sentido(pos_X);                                                   //verifica si se llegó al final de lo permitido en X
        //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        }else{
            pos_Y=-(calcular.sqrt(calcular.abs((pos_X*pos_X)-(radio*radio))));
            pos_X-=4;
            sentido(pos_X);
        }
        ivUser.setTranslateY(pos_Y);                                            //establece la nueva posicion Y
        //System.out.println("posX: "+ivSilla.getTranslateX()+" posY: "+ ivSilla.getTranslateY());
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(double posX){
        //verifica si se llegó al X MAXIMO positivo
        if(posX==radio){
            signo=false;
        //verifica si se llegó al MÍNIMO negativo
        }else if(posX==-radio){
            signo=true;
        }
    }
    //(x)^2 +(y)^2 = 50^2
    private void OrganizeControllerChairs(){
        double distance = 50*4 / listChairs.size();
        double inicio = -50;
        boolean val1=true;
        boolean val2=true;
        int i=0;
        while(!this.listChairP.isEmpty()){
            while(val1){
            double coor_y = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
            this.listChairP.peek().getImage().setTranslateX(inicio);
            this.listChairP.peek().getImage().setTranslateY(coor_y);
            if (i == 0) {
                listChairsGame.addFirst(new Chair(this.listChairP.peek().getImage(),inicio, coor_y));
                i++;
            } else {
                listChairsGame.addLast(new Chair(this.listChairP.peek().getImage(),inicio, coor_y));
            }
            spPane.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio+distance;
            if(inicio>50){
                val1=false;
                inicio=50-distance;
            }
            }
            while(val2 && !this.listChairP.isEmpty()){
            double coor_y = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
             this.listChairP.peek().getImage().setTranslateX(inicio);
            this.listChairP.peek().getImage().setTranslateY(coor_y);
            listChairsGame.addLast(new Chair(this.listChairP.peek().getImage(),inicio, coor_y));
            spPane.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio-distance;
            if(inicio<=(-50)){
                val2=false;
            }
            }
        }
    }
    private void OrganizeControllerUser() {
        double distance = (115*4) / listUsers.size(); //se crea el intervalo de aumento para aumentar la posicion en X
        double inicio = (- 115); //Se proporciona el inicio del intervalo en X es decir su dominio
        boolean val1 = true;
        boolean val2 = true;
        int i = 0;
        while (!this.PileUsers.isEmpty()) { //Cada vez que se ingrese una silla se elimina un objeto de la pila 
            while (val1) {
                double coor_y = (Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2))); //Se saca la posicion en Y segun la ecuacion de la circuferencia
                this.PileUsers.peek().getImage().setTranslateX(inicio); //Se coloca cada silla segun la posioion en X y en Y
                this.PileUsers.peek().getImage().setTranslateY(coor_y);
                if (i == 0) { //Solo la primera se guarda en una lista cada silla con sus posiciones nuevas
                    listUsersGame.addFirst(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y));
                    i++;
                } else {
                    listUsersGame.addLast(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y));
                }
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio + distance; //Se adelante en X
                if (inicio > 115) { //Para que de una vuelta completo y que escoga los valores en Y que estan en la parte inferior de la circuferencia se verifica que no pase del dominio de X
                    val1 = false;
                    inicio = 115 - distance; //Si se llega a salir del dominio se le quita la distancia a la parte final del domininio para la siguiente vuelta
                }
            }
            while (val2 && !this.PileUsers.isEmpty()) { //Entra aqui si y solo aqui sillas por agrgar que corresponderia a las sillas que se iran colacando en la parte inferior de la circuferencia
                double coor_y = -(Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2)));
                this.PileUsers.peek().getImage().setTranslateX(inicio);
                this.PileUsers.peek().getImage().setTranslateY(coor_y);
                listUsersGame.addLast(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y));
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio - distance; //Se va retrocediendo para agregar los valores de "y" que faltan
                if (inicio <= (-115)) {
                    val2 = false;
                }
            }
        }
    }
}
