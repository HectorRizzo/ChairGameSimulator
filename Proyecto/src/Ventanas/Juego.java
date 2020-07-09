
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

    @FXML Button btnPlay= new Button();
    @FXML ImageView ivSilla = new ImageView();
    @FXML Pane spJuego= new StackPane();
    boolean parar= false;
    Image img;
    public Juego() throws FileNotFoundException {
    }

    @FXML protected void btnStopClicked(){
        parar=true;
    }

    @FXML protected void btnPlayClicked() {
        parar=false;
        System.out.println("HOla");
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("1\n");
                        ivSilla.setTranslateX(ivSilla.getTranslateX()+10);
                        ivSilla.setTranslateY(ivSilla.getTranslateY()+10);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                        synchronized (this) {
                            while (parar) {
                                Thread.interrupted();
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
        hilo.start();


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
