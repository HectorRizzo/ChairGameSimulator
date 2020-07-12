package GUI;

import Piece.Chair;
import Piece.Setting;
import Piece.User;
import TDA.LCDE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.applet.AudioClip;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.Deque;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Juego_1 {

    private LCDE<Chair> listChairs = new LCDE();
    private LCDE<Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    private LCDE<User> listUsers = new LCDE();
    private LCDE<User> listUsersGame = new LCDE<>();
    private Deque<User> PileUsers = new ArrayDeque<>();
    private Math calcular;                          //hará las operaciones
    //Desde el FXML 
    @FXML
    Button btnPlay = new Button();
    @FXML
    ImageView ivSilla = new ImageView();
    @FXML
    Pane spPane = new Pane();
    boolean signo = true;                             //nos permite tomar la parte positiva o negativa de Y en la función.
    double radio = 150;                               //radio de la circunferencia a ser dibujada
    double posX = 61;
    double posY = 135;
    boolean parar = false;                           //es el que controla el hilo, al estar en true el hilo de para.
    Image img;
    @FXML
    private ImageView imgMusica;
    private MenuButton mbMusic;
    private MenuItem mi1;
    private MenuItem mi2;
    private Setting sett = new Setting(0);
    AudioClip sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Morado.wav"));

    public Juego_1() throws FileNotFoundException {
    }

    @FXML
    void change(ActionEvent event) {
        sound.stop();
        sound = java.applet.Applet.newAudioClip(getClass().getResource("/GUI/Blinding Lights.wav"));
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
        OrganizeControllerChairs();
        OrganizeControllerUser();

    }

    //mueve al objeto a una posición X,Y según la ecuación
    public void trazarCircunferencia() {
        ivSilla.setTranslateX(posX);
        //verifica que se esté recorriendo hacia la derecha en el eje X
        if (signo) {
            posY = (Math.sqrt(Math.pow(50, 2) - Math.pow(posX - 211, 2))) + 135;     //ecuación de la circunferencia
            posX += 4;                                                         //aumentamos la posición X
            sentido(posX);                                                   //verifica si se llegó al final de lo permitido en X
            //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        } else {
            posY = -(Math.sqrt(Math.pow(50, 2) - Math.pow(posX - 211, 2))) + 135;
            posX -= 4;
            sentido(posX);
        }
        ivSilla.setTranslateY(posY);                                            //establece la nueva posicion Y
        System.out.println("posX: " + ivSilla.getTranslateX() + " posY: " + ivSilla.getTranslateY());
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(double posX) {
        //verifica si se llegó al X MAXIMO positivo
        if (posX == 261) {
            signo = false;
            //verifica si se llegó al MÍNIMO negativo
        } else if (posX == 161) {
            signo = true;
        }
    }
    //(x-211)^2 +(y-135)^2 = 50^2 (Ecuacion para las sillas)

    private void OrganizeControllerChairs() { //Aqui se crea la forma circular de la silla
        double distance = (50 * 4) / listChairs.size(); //se crea el intervalo de aumento para aumentar la posicion en X
        double inicio = (211 - 50); //Se proporciona el inicio del intervalo en X es decir su dominio
        boolean val1 = true;
        boolean val2 = true;
        int i = 0;
        while (!this.listChairP.isEmpty()) { //Cada vez que se ingrese una silla se elimina un objeto de la pila 
            while (val1) {
                double coor_y = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 211, 2))) + 135; //Se saca la posicion en Y segun la ecuacion de la circuferencia
                this.listChairP.peek().getImage().setLayoutX(inicio); //Se coloca cada silla segun la posioion en X y en Y
                this.listChairP.peek().getImage().setLayoutY(coor_y);
                if (i == 0) { //Solo la primera se guarda en una lista cada silla con sus posiciones nuevas
                    listChairsGame.addFirst(new Chair(inicio, coor_y));
                    i++;
                } else {
                    listChairsGame.addLast(new Chair(inicio, coor_y));
                }
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio + distance; //Se adelante en X
                if (inicio > 261) { //Para que de una vuelta completo y que escoga los valores en Y que estan en la parte inferior de la circuferencia se verifica que no pase del dominio de X
                    val1 = false;
                    inicio = 261 - distance; //Si se llega a salir del dominio se le quita la distancia a la parte final del domininio para la siguiente vuelta
                }
            }
            while (val2 && !this.listChairP.isEmpty()) { //Entra aqui si y solo aqui sillas por agrgar que corresponderia a las sillas que se iran colacando en la parte inferior de la circuferencia
                double coor_y = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 211, 2))) + 135;
                this.listChairP.peek().getImage().setLayoutX(inicio);
                this.listChairP.peek().getImage().setLayoutY(coor_y);
                listChairsGame.addLast(new Chair(inicio, coor_y));
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio - distance; //Se va retrocediendo para agregar los valores de "y" que faltan
                if (inicio <= 161) {
                    val2 = false;
                }
            }
        }
    }

    //(x-211)^2 +(y-135)^2 = 150^2 (Ecuacion para las sillas)
    private void OrganizeControllerUser() {
        double distance = (150 * 4) / listUsers.size(); //se crea el intervalo de aumento para aumentar la posicion en X
        double inicio = (211 - 150); //Se proporciona el inicio del intervalo en X es decir su dominio
        boolean val1 = true;
        boolean val2 = true;
        int i = 0;
        while (!this.PileUsers.isEmpty()) { //Cada vez que se ingrese una silla se elimina un objeto de la pila 
            while (val1) {
                double coor_y = (Math.sqrt(Math.pow(150, 2) - Math.pow(inicio - 211, 2))) + 135; //Se saca la posicion en Y segun la ecuacion de la circuferencia
                this.PileUsers.peek().getImage().setLayoutX(inicio); //Se coloca cada silla segun la posioion en X y en Y
                this.PileUsers.peek().getImage().setLayoutY(coor_y);
                if (i == 0) { //Solo la primera se guarda en una lista cada silla con sus posiciones nuevas
                    listUsersGame.addFirst(new User(false, inicio, coor_y));
                    i++;
                } else {
                    listUsersGame.addLast(new User(false, inicio, coor_y));
                }
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio + distance; //Se adelante en X
                if (inicio > 361) { //Para que de una vuelta completo y que escoga los valores en Y que estan en la parte inferior de la circuferencia se verifica que no pase del dominio de X
                    val1 = false;
                    inicio = 361 - distance; //Si se llega a salir del dominio se le quita la distancia a la parte final del domininio para la siguiente vuelta
                }
            }
            while (val2 && !this.PileUsers.isEmpty()) { //Entra aqui si y solo aqui sillas por agrgar que corresponderia a las sillas que se iran colacando en la parte inferior de la circuferencia
                double coor_y = -(Math.sqrt(Math.pow(150, 2) - Math.pow(inicio - 211, 2))) + 135;
                this.PileUsers.peek().getImage().setLayoutX(inicio);
                this.PileUsers.peek().getImage().setLayoutY(coor_y);
                listUsersGame.addLast(new User(false, inicio, coor_y));
                spPane.getChildren().addAll(this.PileUsers.pop().getImage());
                inicio = inicio - distance; //Se va retrocediendo para agregar los valores de "y" que faltan
                if (inicio <= 61) {
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
