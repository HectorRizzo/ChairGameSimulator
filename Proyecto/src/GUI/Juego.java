
package GUI;

import Piece.Chair;
import Piece.Setting;
import Piece.User;
import TDA.LCDE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Juego{
    private LCDE<Chair> listChairs = new LCDE();
    private LCDE <Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    private LCDE<User> listUsers = new LCDE();
    private LCDE<User> listUsersGame = new LCDE<>();
    private Deque<User> PileUsers = new ArrayDeque<>();
    private TreeMap<Chair,User> mapaDistancia=new TreeMap<>();          //mapa que guarda las sillas y el user que se sentarán en ella
    private Deque<Chair> pilaChairOccupated= new LinkedList<>();             //pila que guarda las sillas de manera que cuando una ya esté ocupada se le hace pop
    
//Desde el FXML
    @FXML Button btnPlay= new Button();
    @FXML Pane spPane= new StackPane();
    boolean parar= false;                           //es el que controla el hilo, al estar en true el hilo de para.
    private Setting sett = new Setting(0);
     

    
    public Juego() throws FileNotFoundException {
    }

    //cuando se presiona el botón stop el hilo se interrumpirá.
    @FXML protected void btnStopClicked(){
        parar=true;
        calcularDistancia();
        Thread hiloSentarse = new Thread(new Runnable() {
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    //aqui se pone lo que se quiere ejecutar en el tiempo
                    public void run() {
                        //recorre el mapa y los hace sentarse alrededor de las sillas
                        for(Map.Entry<Chair,User> entry : mapaDistancia.entrySet()) {
                            sentarJugadores(entry.getValue(),entry.getKey());
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(100);        //establece cada cuanto tiempo se ejecutará la acción del run
                        synchronized (this) {
                            //mientras la pila de sillas no esté vacía se sigue ejecutando el hilo
                            while (pilaChairOccupated.isEmpty()) {
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
        hiloSentarse.setDaemon(true);
        hiloSentarse.start();

    }

    //define que se hará al presionar al botón play
    @FXML protected void btnPlayClicked()  {
        mapaDistancia.clear();
        parar=false;                                //se lo pone en false porque puede que antes se haya dado al botón stop.
        
        //moverá las pelotas en el tiempo
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    //aqui se pone lo que se quiere ejecutar en el tiempo
                    public void run() {
                        //recorre la lista de jugadores y los hace caminar alrededor de las sillas
                        for(int i=0;i<listUsersGame.size();i++){
                            trazarCircunferencia(listUsersGame.get(i),115);
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(30);        //establece cada cuanto tiempo se ejecutará la acción del run
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


    public void initialize(Double nP, String sentido) throws MalformedURLException {
        sett = new Setting(nP,sentido);
        listChairs = sett.addChairs(); //En Seeting crea la lista de sillas
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


    //hace que el jugador se muevan en circulo. Sentido true= se mueve hacia la derecha, sentido= false: se mueve hacia la izquierda
    public void trazarCircunferencia(User user,double initialRadio) {
        if(sett.getDirection().equalsIgnoreCase("Antihorario")){
        double posX=user.getPosX();
        double posY;
        //verifica que se esté recorriendo hacia la derecha en el eje X
        sentido(user,posX,initialRadio);                                //verifica si se llegó al final de lo permitido en X
        //comprueba si se mueve hacia la derecha o la izquierda de las X
        if (user.isSentido()) {
            posY = Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow((posX), 2))) ;     //ecuación de la circunferencia
            posX += 1;                                                         //aumentamos la posición X
            //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        } else {
            posY = -Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow(posX, 2))) ;
            posX -= 1;
        }
        user.setPosX(posX);
        user.setPosY(posY);                                                             //guarda la posición x,y en el usuario
        user.getImage().setTranslateX(posX);
        user.getImage().setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("; posX: "+posX+" posY: "+posY);
        }else if(sett.getDirection().equalsIgnoreCase("Horario")){
        double posX=user.getPosX();
        double posY;
        //verifica que se esté recorriendo hacia la derecha en el eje X
        sentido(user,posX,initialRadio);                                //verifica si se llegó al final de lo permitido en X
        //comprueba si se mueve hacia la derecha o la izquierda de las X
        if (user.isSentido()) {
            posY = -Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow((posX), 2))) ;     //ecuación de la circunferencia
            posX += 1;                                                         //aumentamos la posición X
            //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        } else {
            posY = Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow(posX, 2))) ;
            posX -= 1;
        }
        user.setPosX(posX);
        user.setPosY(posY);                                                             //guarda la posición x,y en el usuario
        user.getImage().setTranslateX(posX);
        user.getImage().setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("; posX: "+posX+" posY: "+posY);
        }
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(User user,double posX,double radio){
        //verifica si se llegó al X MAXIMO positivo
        if(posX==radio){
            System.out.println("entra en radio");
            user.setSentido(false);
        //verifica si se llegó al MÍNIMO negativo
        }else if(posX==-radio){
            System.out.println("entra en -radio");
            user.setSentido(true);
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
        double distance = (114*4) / listUsers.size(); //se crea el intervalo de aumento para aumentar la posicion en X
        double inicio = (- 114); //Se proporciona el inicio del intervalo en X es decir su dominio
        boolean val1 = true;
        boolean val2 = true;
        int i = 0;
        while (!this.PileUsers.isEmpty()) { //Cada vez que se ingrese una silla se elimina un objeto de la pila
            while (val1) {
                double coor_y = (Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2))); //Se saca la posicion en Y segun la ecuacion de la circuferencia
                this.PileUsers.peek().getImage().setTranslateX(inicio); //Se coloca cada silla segun la posioion en X y en Y
                this.PileUsers.peek().getImage().setTranslateY(coor_y);
                if (i == 0) { //Solo la primera se guarda en una lista cada silla con sus posiciones nuevas
                    listUsersGame.addFirst(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y,true));  //usa el constructor que también añade el sentido en que se moverá
                    i++;
                } else {
                    listUsersGame.addLast(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y,true));
                }
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio + distance; //Se adelante en X
                if (inicio > 114) { //Para que de una vuelta completo y que escoga los valores en Y que estan en la parte inferior de la circuferencia se verifica que no pase del dominio de X
                    val1 = false;
                    inicio = 114 - distance; //Si se llega a salir del dominio se le quita la distancia a la parte final del domininio para la siguiente vuelta
                }
            }
            while (val2 && !this.PileUsers.isEmpty()) { //Entra aqui si y solo aqui sillas por agrgar que corresponderia a las sillas que se iran colacando en la parte inferior de la circuferencia
                double coor_y = -(Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2)));
                this.PileUsers.peek().getImage().setTranslateX(inicio);
                this.PileUsers.peek().getImage().setTranslateY(coor_y);
                listUsersGame.addLast(new User(this.PileUsers.peek().getImage(),false, inicio, coor_y,false));
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio - distance; //Se va retrocediendo para agregar los valores de "y" que faltan
                if (inicio <= (-115)) {
                    val2 = false;
                }
            }
        }
    }

    //calcula la distancia minima de cada silla con los jugadores y los agrega al mapa
    public void calcularDistancia(){
        //recorre la lista de sillas
        for(int i=0;i<listChairsGame.size();i++){
            Stack<User> pilaUserSentado=new Stack<>();
            double distanciaMinima=1000000000;
            //recorre la lista de users
            for (int j=0;j<listUsersGame.size();j++){
                //Verifica si el usuario no se a sentado aún
                if(!listUsersGame.get(j).isSeated()){
                    //guarda las posiciones x,y de la silla y los users
                    double posXChair=listChairsGame.get(i).getPos_X();
                    double posYChair=listChairsGame.get(i).getPos_y();
                    double posXUser=listUsersGame.get(j).getPosX();
                    double posYUser=listUsersGame.get(j).getPosY();
                    double distancia=Math.sqrt((Math.pow(posXUser-posXChair,2)+Math.pow(posYUser-posYChair,2))); //calcula la distancia minima entre dos puntos: raiz((X-x)^2 + (Y-y)^2))
                    //verifica si la distancia del usuario es minima a la distancia anterior
                    if(distancia<distanciaMinima){
                        distanciaMinima=distancia;
                        //comprueba si la pila tiene elementos
                        if(!pilaUserSentado.isEmpty()){
                            System.out.println(pilaUserSentado.pop());              //elimina el user anterior
                            pilaUserSentado.push(listUsersGame.get(j));             //agrega el nuevo user
                        }else{
                            pilaUserSentado.push(listUsersGame.get(j));             //agrega el user
                        }
                    }
                }
            }
            pilaUserSentado.peek().setSeated(true);                                 //pone el estado del user en sentado para que no se vuelva a consultar
            pilaChairOccupated.push(listChairsGame.get(i));                         //agrega la silla a la pila de sillas
            mapaDistancia.put(listChairsGame.get(i),pilaUserSentado.pop());         //agrega la silla y el usuario al mapa
        }

    }

    //efectúa la animación de sentar al jugador; se utilizará la ecuación de la recta Y - y = m (X - x) para que el user "camine" en línea recta
    public void sentarJugadores(User user, Chair chair){
        double posX=0;
        double pendiente= ((chair.getPos_y()-user.getPosY())/(chair.getPos_X()-user.getPosX()));        //se obtiene la pendiente de la recta
        //comprueba que la silla no esté ya ocupada (porque el hilo ejecuta la acción para todos, por eso se debe verificar)
        if(!chair.isOccupated()){
            //verifica que el user no haya llegado a la silla
            if(user.getPosX()!=chair.getPos_X()||user.getPosY()!=chair.getPos_y()){
                if(user.getPosX()>chair.getPos_X()){            //si la pos X del user está despues de la posX de la silla, disminuye la posX y con ella se obtiene la posY
                    posX= user.getPosX()-1;
                    user.setPosX(posX);
                }else if(user.getPosX()<chair.getPos_X()){      //si la pos X del user está antes de la posX de la silla, aumenta la posX y con ella se obtiene la posY
                    posX= user.getPosX()+1;
                    user.setPosX(posX);
                }
                double posY= pendiente * (posX-chair.getPos_X()) + chair.getPos_y();        //se calcula la posY
                //se establece las posiciones en los atributos del user y se lo mueve
                user.setPosX(posX);
                user.setPosY(posY);
                user.getImage().setTranslateX(posX);
                user.getImage().setTranslateY(posY);
            }else{
                //si ya se llegó a la silla, se la marca como ocupada y se la elimina de la pila
                chair.setOccupated(true);
                pilaChairOccupated.pop();
            }
        }
    }
    public void closeWindows(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/configuraciones.fxml"));
            Parent root = loader.load();
            configuraciones controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.show();
        } catch(IOException ex){
            Logger.getLogger(configuraciones.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
