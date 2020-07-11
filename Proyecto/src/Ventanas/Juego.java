
package Ventanas;

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
import java.util.ResourceBundle;


public class Juego implements Initializable {

    private Math calcular;                          //hará las operaciones
    //Desde el FXML
    @FXML Button btnPlay= new Button();
    @FXML ImageView ivSilla = new ImageView();
    @FXML Pane spJuego= new StackPane();
    boolean signo=true;                             //nos permite tomar la parte positiva o negativa de Y en la función.
    double radio=100;                               //radio de la circunferencia a ser dibujada
    double posX=-radio;
    double posY=0;
    boolean parar= false;                           //es el que controla el hilo, al estar en true el hilo de para.
    Image img;
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
    }

    //mueve al objeto a una posición X,Y según la ecuación
    public void trazarCircunferencia(){
        ivSilla.setTranslateX(posX);
        //verifica que se esté recorriendo hacia la derecha en el eje X
        if(signo){
            posY=calcular.sqrt(calcular.abs((posX*posX)-(radio*radio)));     //ecuación de la circunferencia
            posX+=4;                                                         //aumentamos la posición X
            sentido(posX);                                                   //verifica si se llegó al final de lo permitido en X
        //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        }else{
            posY=-(calcular.sqrt(calcular.abs((posX*posX)-(radio*radio))));
            posX-=4;
            sentido(posX);
        }
        ivSilla.setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("posX: "+ivSilla.getTranslateX()+" posY: "+ ivSilla.getTranslateY());
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

}
