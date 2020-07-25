package controller;

import piece.Chair;
import piece.Setting;
import piece.User;
import tda.LCDE;
import java.io.File;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Juego {

    public Button btnDirection;
    public MenuItem mi;
    private LCDE<Chair> listChairs = new LCDE<>();
    private final LCDE<Chair> listChairsGame = new LCDE<>();
    private final Deque<Chair> listChairP = new ArrayDeque<>();
    private LCDE<User> listUsers = new LCDE<>();
    private final LCDE<User> listUsersGame = new LCDE<>();
    private final Deque<User> pileUsers = new ArrayDeque<>();
    private final Map<Chair, User> mapaDistancia = new LinkedHashMap<>();          //mapa que guarda las sillas y el user que se sentarán en ella
    private final Deque<Chair> pilaChairOccupated = new LinkedList<>();             //pila que guarda las sillas de manera que cuando una ya esté ocupada se le hace pop
    private final Deque<User> pilaUserSentado = new LinkedList<>();
    private int season = 0;
    private int velocity=15;
     Stage windows;
//Desde el FXML
    @FXML Button btnPlay ;
    @FXML Button btnStop;
    @FXML Pane spPane = new StackPane();
    boolean parar = false;                           //es el que controla el hilo, al estar en true el hilo de para.
    private Setting sett = new Setting(0);
    @FXML private MenuButton mbMusic;
    @FXML private Slider slVelocity;            //slider que controla la velocidad de los jugadores
    @FXML private MenuItem mi2;
    @FXML private MenuItem mi1;

    @FXML private ImageView ivMusic;
    Media sound= new Media(new File("src/Files/Scatman.mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    
    public Juego() {
        //constructor
    }

    @FXML
    void change(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Scatman");
        sound= new Media(new File("src/Files/Scatman.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }

    }

    @FXML
    void change1(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Morado");
        sound= new Media(new File("src/Files/Morado.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }
    }

    @FXML
    void change2(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Blinding Lights");
        sound= new Media(new File("src/Files/Blinding Lights.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }
    }

    @FXML
    void btnDirectionClicked(){
        tda.ListIterator <User> it= listUsersGame.iterator();
        while(it.limit()){
            User u= it.previous();
            u.setSentido(!u.isSentido());
        }

        changeDirection();
    }

    //cuando se presiona el botón stop el hilo se interrumpirá.
    @FXML
    protected void btnStopClicked(){
        mediaPlayer.stop();
        btnStop.setDisable(true);       
        parar = true;
        season++;
        calcularDistancia();
        Thread hiloSentarse = new Thread(new Runnable() {
            public void run() {
                //aqui se pone lo que se quiere ejecutar en el tiempo
                Runnable updater = () -> {
                    //recorre el mapa y los hace sentarse alrededor de las sillas
                    for (Map.Entry<Chair, User> entry : mapaDistancia.entrySet()) {
                        sentarJugadores(entry.getValue(), entry.getKey());
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(100);        //establece cada cuanto tiempo se ejecutará la acción del run
                        synchronized (this) {
                            //mientras la pila de sillas no esté vacía se sigue ejecutando el hilo
                            while (pilaChairOccupated.isEmpty()) {

                                btnPlay.setDisable(false);
                                Thread.interrupted();       //se interrumpe el hilo
                                wait();
                            }

                          }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }

            }
        }
        );
        if (listUsersGame.size() == 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensjase de aviso");
            alert.setContentText("Juego terminado");
            alert.setHeaderText(null);
            alert.show();
            alert.setOnCloseRequest(e->{
            windows.close();
            this.closeWindows();
            });
        }
        hiloSentarse.setDaemon(true);
        hiloSentarse.start();

    }

    //define que se hará al presionar al botón play
    @FXML
    protected void btnPlayClicked() {
        if(season>0){
            this.changeDirection();
        }
        btnPlay.setDisable(true);
        btnStop.setDisable(false);
        mediaPlayer.play();
        parar = false;                                //se lo pone en false porque puede que antes se haya dado al botón stop.
        if (season > 0) {
            this.spPane.getChildren().clear();
            this.saveChairsUser();
            this.organizeControllerChairs();
            this.organizeControllerUser();
        }

        this.mapaDistancia.clear();
        //moverá las pelotas en el tiempo
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                //aqui se pone lo que se quiere ejecutar en el tiempo
                Runnable updater = () -> {
                    //recorre la lista de jugadores y los hace caminar alrededor de las sillas
                    for (int i = 0; i < listUsersGame.size(); i++) {
                        trazarCircunferencia(listUsersGame.get(i), 115, i);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(velocity);        //establece cada cuanto tiempo se ejecutará la acción del run
                        synchronized (this) {
                            while (parar) {
                                Thread.interrupted();       //se interrumpe el hilo
                                wait();
                            }
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();           //inicia el hilo
    }

    public void initialize(Double nP, String sentido, Stage stage) {
        windows=stage;
        sett = new Setting(nP, sentido);
        listChairs = sett.addChairs(); //En Seeting crea la lista de sillas
        listUsers = sett.addPlayers();
        //accion de incrementar la velocidad de los jugadores
        //Se activa al notar un cambio
        this.slVelocity.valueProperty().addListener((observableValue, number, t1) -> {
            //si el slider marca más de 95 se fija en 2 la velocidad porque puede dar un numero tan cercano a 0 ó 0 y el programa se caería.
            if (t1.intValue() > 95) {
                velocity = 2;
            } else {
                velocity = 40 - Math.round(40 * (t1.floatValue() / 100));
            }
        });


        for (int i = 0; i < listChairs.size(); i++) { //Guarda cada silla en una pila
            listChairP.push(listChairs.get(i));
        }
        for (int i = 0; i < listUsers.size(); i++) {
            pileUsers.push(listUsers.get(i));
        }
        organizeControllerChairs();
        organizeControllerUser();
    }

    //hace que el jugador se muevan en circulo. Sentido true= se mueve hacia la derecha, sentido= false: se mueve hacia la izquierda
    public void trazarCircunferencia(User user, double initialRadio, int i) {
        if (sett.getDirection().equalsIgnoreCase("Antihorario")) {
            recorrerSentido(user,initialRadio,i,true);

        } else if (sett.getDirection().equalsIgnoreCase("Horario")) {
            recorrerSentido(user,initialRadio,i,false);
        }
    }

    //nos dice si se llegó al final de lo permitido en el eje X; si es true se seguirá recorriendo hacia la derecha del eje
    public void sentido(User user, double posX, double radio) {
        //verifica si se llegó al X MAXIMO positivo
        if (posX +1 > radio) {
            user.setSentido(false);
            //verifica si se llegó al MÍNIMO negativo
        } else if (posX -1 < -radio) {
            user.setSentido(true);
        }
    }

    //(x)^2 +(y)^2 = 50^2
    private void organizeControllerChairs() {
        double distance = 50 * 4 / listChairs.size();
        double inicio = -50;
        boolean val1 = true;
        boolean val2 = true;
        while (!this.listChairP.isEmpty()) {
            while (val1) {
                double cordy = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
                assert this.listChairP.peek() != null;
                this.listChairP.peek().getImage().setTranslateX(inicio);
                assert this.listChairP.peek() != null;
                this.listChairP.peek().getImage().setTranslateY(cordy);
                assert this.listChairP.peek() != null;
                listChairsGame.addLast(new Chair(this.listChairP.peek().getImage(), inicio, cordy));
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio + distance;
                if (inicio > 50) {
                    val1 = false;
                    inicio = 50 - distance;
                }
            }
            while (val2 && !this.listChairP.isEmpty()) {
                double cordy = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio, 2)));
                this.listChairP.peek().getImage().setTranslateX(inicio);
                assert this.listChairP.peek() != null;
                this.listChairP.peek().getImage().setTranslateY(cordy);
                assert this.listChairP.peek() != null;
                listChairsGame.addLast(new Chair(this.listChairP.peek().getImage(), inicio, cordy));
                spPane.getChildren().addAll(this.listChairP.pop().getImage());
                inicio = inicio - distance;
                if (inicio <= (-50)) {
                    val2 = false;
                }
            }
        }
    }

    private void organizeControllerUser() {
        double distance = (115 * 4) / listUsers.size(); //se crea el intervalo de aumento para aumentar la posicion en X
        double inicio; //Se proporciona el inicio del intervalo en X es decir su dominio
        boolean val1 = true;
        boolean val2 = true;
        while (!this.pileUsers.isEmpty()) { //Cada vez que se ingrese una silla se elimina un objeto de la pila
            inicio=-115;
            while (val1) {
                double cordY = -(Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2))); //Se saca la posicion en Y segun la ecuacion de la circuferencia
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateX(inicio); //Se coloca cada silla segun la posioion en X y en Y
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateY(cordY);

                assert this.pileUsers.peek() != null;
                listUsersGame.addLast(new User(this.pileUsers.peek().getImage(), false, inicio, cordY, true));

                spPane.getChildren().addAll(this.pileUsers.pop().getImage());
                inicio = inicio + distance; //Se adelante en X
                if (inicio > 115) { //Para que de una vuelta completo y que escoga los valores en Y que estan en la parte inferior de la circuferencia se verifica que no pase del dominio de X
                    val1 = false;
                    inicio = 115 - (distance / 2); //Si se llega a salir del dominio se le quita la distancia a la parte final del domininio para la siguiente vuelta
                }
            }
            while (val2 && !this.pileUsers.isEmpty()) { //Entra aqui si y solo aqui sillas por agrgar que corresponderia a las sillas que se iran colacando en la parte inferior de la circuferencia
                double cordy = (Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2)));
                this.pileUsers.peek().getImage().setTranslateX(inicio);
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateY(cordy);
                assert this.pileUsers.peek() != null;
                listUsersGame.addLast(new User(this.pileUsers.peek().getImage(), false, inicio, cordy, false));
                spPane.getChildren().addAll(this.pileUsers.pop().getImage());
                inicio = inicio - distance; //Se va retrocediendo para agregar los valores de "y" que faltan
                if (inicio <= (-115)) {
                    val2 = false;
                }
            }
        }

    }

    //calcula la distancia minima de cada silla con los jugadores y los agrega al mapa
    public void calcularDistancia() {
        //recorre la lista de sillas
        int sentado = 0;
        for (int i = 0; i < listChairsGame.size(); i++) {
            double distanciaMinima = 1000000000;
            //recorre la lista de users
            for (int j = 0; j < listUsersGame.size(); j++) {
                //Verifica si el usuario no se a sentado aún
                if (!listUsersGame.get(j).isSeated()) {
                    //guarda las posiciones x,y de la silla y los users
                    double posXChair = listChairsGame.get(i).getPosX();
                    double posYChair = listChairsGame.get(i).getPosY();
                    double posXUser = listUsersGame.get(j).getPosX();
                    double posYUser = listUsersGame.get(j).getPosY();
                    double distancia = Math.sqrt((Math.pow(posXChair - posXUser, 2) + Math.pow(posYChair - posYUser, 2))); //calcula la distancia minima entre dos puntos: raiz((X-x)^2 + (Y-y)^2))
                    //verifica si la distancia del usuario es minima a la distancia anterior
                    if (distancia < distanciaMinima) {
                        distanciaMinima = distancia;
                        if (!pilaUserSentado.isEmpty()) {
                            pilaUserSentado.pop();
                        }
                        pilaUserSentado.push(listUsersGame.get(j));
                        sentado = j;
                    }
                }
            }
            listUsersGame.get(sentado).setSeated(true);                                 //pone el estado del user en sentado para que no se vuelva a consultar
            pilaChairOccupated.push(listChairsGame.get(i));                         //agrega la silla a la pila de sillas
            mapaDistancia.put(listChairsGame.get(i), pilaUserSentado.pop());         //agrega la silla y el usuario al mapa
        }

    }
    public void saveChairsUser(){
        this.listUsers.clear();
        this.listUsersGame.clear();
        this.listChairs.clear();
        this.listChairsGame.clear();
        for (Map.Entry<Chair, User> entry : mapaDistancia.entrySet()){
            this.listUsers.addLast(entry.getValue());
            this.listChairs.addLast(entry.getKey());
        }
        for (int i = 0; i < listUsers.size(); i++) {
            pileUsers.push(listUsers.get(i));
        }
        for (int i = 0; i < listChairs.size(); i++) { //Guarda cada silla en una pila
            listChairP.push(listChairs.get(i));
        }
        this.listChairs.removefirst();
        this.listChairP.removeLast();
    }
    //efectúa la animación de sentar al jugador; se utilizará la ecuación de la recta Y - y = m (X - x) para que el user "camine" en línea recta
    public void sentarJugadores(User user, Chair chair) {
        btnPlay.setDisable(true);
        double posX = 0;
        //comprueba que la silla no esté ya ocupada (porque el hilo ejecuta la acción para todos, por eso se debe verificar)
        if (!chair.isOccupated()) {
            
            double pendiente = ((chair.getPosY() - user.getPosY()) / (chair.getPosX() - user.getPosX()));        //se obtiene la pendiente de la recta
            //verifica que el user no haya llegado a la silla
            if (user.getPosX() != chair.getPosX() || user.getPosY() != chair.getPosY()) {
                double posY = 0;
                if(chair.getPosX()==user.getPosX()){
                    double aumento=Math.abs((user.getPosY()-chair.getPosY())/23);
                    if(user.getPosY()>0){
                        posY=user.getPosY()-aumento;
                    }else{
                        posY=user.getPosY()+aumento;
                    }
                } else if (user.getPosX() > chair.getPosX()) {            //si la pos X del user está despues de la posX de la silla, disminuye la posX y con ella se obtiene la posY
                    double aumento=Math.abs((user.getPosX()-chair.getPosX())/23);
                    posX = user.getPosX() - aumento;
                    user.setPosX(posX);
                    posY = pendiente * (posX - chair.getPosX()) + chair.getPosY();        //se calcula la posY
                } else if (user.getPosX() < chair.getPosX()) {      //si la pos X del user está antes de la posX de la silla, aumenta la posX y con ella se obtiene la posY
                    double aumento=Math.abs((user.getPosX()-chair.getPosX())/23);
                    posX = user.getPosX() + aumento;
                    user.setPosX(posX);
                    posY = pendiente * (posX - chair.getPosX()) + chair.getPosY();        //se calcula la posY
                }
                //se establece las posiciones en los atributos del user y se lo mueve
                user.setPosX(posX);
                user.setPosY(posY);
                user.getImage().setTranslateX(posX);
                user.getImage().setTranslateY(posY);
            }
            if(Math.abs(user.getPosX()-chair.getPosX())<4 && Math.abs(user.getPosY()-chair.getPosY())<4){
                //si ya se llegó a la silla, se la marca como ocupada y se la elimina de la pila
                chair.setOccupated(true);
                pilaChairOccupated.pop();
            }


        }
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Configurations.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Configurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void changeDirection(){
        tda.ListIterator <User> it= listUsersGame.iterator();
        //Usamos el método hasNext, para comprobar si hay algun elemento
        while(it.limit()){
            it.next().setSentido(!it.next().isSentido());
            //El iterador devuelve el proximo elemento
        }
        if (sett.getDirection().equalsIgnoreCase("Antihorario")) {
            sett.setDirection("Horario");
        }else{
            sett.setDirection("Antihorario");
        }

    }

    //hace que los jugadores se muevan según la dirección dada; true para sentido antihorario
    public void recorrerSentido(User user, double initialRadio, int i,boolean direction){
        double posX = user.getPosX();
        double posY;
        int signoA= -1;
        int signoB= 1;
        if(direction){
            signoA=1;
            signoB=-1;
        }
        //verifica que se esté recorriendo hacia la derecha en el eje X
        sentido(user, posX, initialRadio);                                //verifica si se llegó al final de lo permitido en X
        //comprueba si se mueve hacia la derecha o la izquierda de las X
        if (user.isSentido()) {
            posY = signoA *Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow((posX), 2)));     //ecuación de la circunferencia
            posX += 1;                                                         //aumentamos la posición X
            //Sino se cumple lo anterior, se tomará la parte negativa de la raíz cuadrada de la ecuación
        } else {
            posY = signoB * Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow(posX, 2)));
            posX -= 1;
        }
        listUsersGame.get(i).setPosX(posX);
        listUsersGame.get(i).setPosY(posY);                                                        //guarda la posición x,y en el usuario
        user.getImage().setTranslateX(posX);
        user.getImage().setTranslateY(posY);

    }
}
