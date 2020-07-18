package GUI;

import Piece.Chair;
import Piece.Setting;
import Piece.User;
import TDA.LCDE;
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
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class Juego implements Initializable {
    
    private LCDE<User> listUsers = new LCDE();//lista de usuarios
     private Deque<User> PileUsers = new ArrayDeque<>();

    private LCDE<Chair> listChairs = new LCDE();
    private LCDE<Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    private Math calcular;                          //hará las operaciones
    //Desde el FXML
    @FXML
    Button btnPlay = new Button();
    @FXML
    ImageView ivSilla = new ImageView();
    @FXML
    Pane spPane = new StackPane();
    boolean signo = true;                             //nos permite tomar la parte positiva o negativa de Y en la función.
    double radio = 50;                               //radio de la circunferencia a ser dibujada
    double posX = -radio;
    double posY = 0;
    boolean parar = false;                           //es el que controla el hilo, al estar en true el hilo de para.
    Image img;
    private Setting sett = new Setting(4);
    @FXML
    private MenuItem mi;
    private MenuItem mi1;
    private MenuItem mi2;

    AudioClip sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Morado.wav"));

    public Juego() throws FileNotFoundException {
    }

    @FXML
    void change(ActionEvent event) {
        sound.stop();
        sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Scatman.wav"));
        if (!parar) {
            sound.play();
        }

    }

    @FXML
    void change1(ActionEvent event) {
        sound.stop();
        sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Morado.wav"));
        if (!parar) {
            sound.play();
        }
    }

    @FXML
    void change2(ActionEvent event) {
        sound.stop();
        sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Blinding Lights.wav"));
        if (!parar) {
            sound.play();
        }
    }

    //cuando se presiona el botón stop el hilo se interrumpirá.
    @FXML
    protected void btnStopClicked() {
        sound.stop();
        parar = true;

    }

    //define que se hará al presionar al botón play
    @FXML
    protected void btnPlayClicked() {
        sound.play();

        parar = false;                                //se lo pone en false porque puede que antes se haya dado al botón stop.

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
    
     public void initialize(Double nP) throws MalformedURLException { //Se ejecuta este inicializado para llenar todo lo que dijo el usario antes que se abra esta ventana
        sett = new Setting(nP);
        listChairs = sett.addChairs(); //En Seeting crera la lista de sillas
        listUsers = sett.addPlayers();
        for (int i = 0; i < listChairs.size(); i++) { //Guarda cada silla en una pila
            listChairP.push(listChairs.get(i));
        }
        for (int i = 0; i < listUsers.size(); i++) {
            PileUsers.push(listUsers.get(i));
        }
        OrganizeController();
        

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setListChairs();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
        OrganizeController();
    }

    //mueve al objeto a una posición X,Y según la ecuación
    public void trazarCircunferencia() {
        ivSilla.setTranslateX(posX);
        //verifica que se esté recorriendo hacia la derecha en el eje X
        if (signo) {
            posY = calcular.sqrt(calcular.abs((posX * posX) - (radio * radio)));     //ecuación de la circunferencia
            posX += 4;                                                         //aumentamos la posición X
            sentido(posX);                                                   //verifica si se llegó al final de lo permitido en X
            //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        } else {
            posY = -(calcular.sqrt(calcular.abs((posX * posX) - (radio * radio))));
            posX -= 4;
            sentido(posX);
        }
        ivSilla.setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("posX: " + ivSilla.getTranslateX() + " posY: " + ivSilla.getTranslateY());
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(double posX) {
        //verifica si se llegó al X MAXIMO positivo
        if (posX == radio) {
            signo = false;
            //verifica si se llegó al MÍNIMO negativo
        } else if (posX == -radio) {
            signo = true;
        }
    }

    public void setListChairs() throws MalformedURLException {
        listChairs = sett.addChairs();
        for (int i = 0; i < listChairs.size(); i++) {
            listChairP.push(listChairs.get(i));
        }
    }

    void iniData(Setting set) throws MalformedURLException {
        this.sett = set;
        listChairs = sett.addChairs();
        for (int i = 0; i < listChairs.size(); i++) {
            listChairP.push(listChairs.get(i));
        }

    }

    //(x)^2 +(y)^2 = 50^2
    private void OrganizeController() {
        double distance = this.radio * 4 / listChairs.size();
        double inicio = -this.radio;
        boolean val1 = true;
        boolean val2 = true;
        int i = 0;
        while (!this.listChairP.isEmpty()) {
            while (val1) {
                double coor_y = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
                this.listChairP.peek().getImage().setLayoutX(inicio);
                this.listChairP.peek().getImage().setLayoutY(coor_y);
                if (i == 0) {
                    listChairsGame.addFirst(new Chair(inicio, coor_y));
                    i++;
                } else {
                    listChairsGame.addLast(new Chair(inicio, coor_y));
                }
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio + distance;
                if (inicio > 50) {
                    val1 = false;
                    inicio = 50 - distance;
                }
            }
            while (val2 && !this.listChairP.isEmpty()) {
                double coor_y = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
                this.listChairP.peek().getImage().setLayoutX(inicio);
                this.listChairP.peek().getImage().setLayoutY(coor_y);
                listChairsGame.addLast(new Chair(inicio, coor_y));
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio - distance;
                if (inicio <= (-50)) {
                    val2 = false;
                }
            }
        }
    }

    public AudioClip getSound() {
        return sound;
    }

    public void setSound(AudioClip sound) {
        this.sound = sound;
    }

}
