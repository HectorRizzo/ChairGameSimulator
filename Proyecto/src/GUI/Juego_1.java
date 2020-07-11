
package GUI;

import Piece.Chair;
import Piece.Setting;
import TDA.LCDE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ResourceBundle;


public class Juego_1 implements Initializable {
    private LCDE<Chair> listChairs = new LCDE();
    private LCDE <Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    private Math calcular;                          //hará las operaciones
    //Desde el FXML 
    @FXML Button btnPlay= new Button();
    @FXML ImageView ivSilla = new ImageView();
    @FXML Pane spPane= new Pane();
    boolean signo=true;                             //nos permite tomar la parte positiva o negativa de Y en la función.
    double radio=150;                               //radio de la circunferencia a ser dibujada
    double posX=61;
    double posY=135;
    boolean parar= false;                           //es el que controla el hilo, al estar en true el hilo de para.
    Image img;
    private Setting sett = new Setting(5);
    
    public Juego_1() throws FileNotFoundException {
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
                        trazarCircunferencia();    //llama a la función que mueve las figuras
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListChairs();
        OrganizeController();
    }
    
    //mueve al objeto a una posición X,Y según la ecuación
    public void trazarCircunferencia(){
        ivSilla.setTranslateX(posX);
        //verifica que se esté recorriendo hacia la derecha en el eje X
        if(signo){
            posY= (Math.sqrt(Math.pow(50, 2) - Math.pow(posX - 211, 2))) + 135;     //ecuación de la circunferencia
            posX+=4;                                                         //aumentamos la posición X
            sentido(posX);                                                   //verifica si se llegó al final de lo permitido en X
        //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        }else{
            posY=-(Math.sqrt(Math.pow(50, 2) - Math.pow(posX - 211, 2))) + 135;
            posX-=4;
            sentido(posX);
        }
        ivSilla.setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("posX: "+ivSilla.getTranslateX()+" posY: "+ ivSilla.getTranslateY());
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(double posX){
        //verifica si se llegó al X MAXIMO positivo
        if(posX==261){
            signo=false;
        //verifica si se llegó al MÍNIMO negativo
        }else if(posX==161){
            signo=true;
        }
    }

    public void setListChairs() {
        listChairs=sett.addChairs();
        for(int i=0; i<listChairs.size();i++){
            listChairP.push(listChairs.get(i));
        }
    }
    
    void iniData(Setting set){
        this.sett=set;
        listChairs=sett.addChairs();
        for(int i=0; i<listChairs.size();i++){
            listChairP.push(listChairs.get(i));
        }
    
    }
    //(x)^2 +(y)^2 = 50^2
    private void OrganizeController(){
        double distance = (50*4) / listChairs.size();
        double inicio = (211-50);
        boolean val1=true;
        boolean val2=true;
        int i=0;
        while(!this.listChairP.isEmpty()){
            while(val1){
            double coor_y = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 211, 2))) + 135;
            this.listChairP.peek().getImage().setLayoutX(inicio);
            this.listChairP.peek().getImage().setLayoutY(coor_y);
            if (i == 0) {
                listChairsGame.addFirst(new Chair(inicio, coor_y));
                i++;
            } else {
                listChairsGame.addLast(new Chair(inicio, coor_y));
            }
            spPane.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio+distance;
            if(inicio>261){
                val1=false;
                inicio=261-distance;
            }
            }
            while(val2 && !this.listChairP.isEmpty()){
            double coor_y = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 211, 2))) + 135;
             this.listChairP.peek().getImage().setLayoutX(inicio);
             this.listChairP.peek().getImage().setLayoutY(coor_y);
            listChairsGame.addLast(new Chair(inicio, coor_y));
            spPane.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio-distance;
            if(inicio<=161){
                val2=false;
            }
            }
        }
    }
}
