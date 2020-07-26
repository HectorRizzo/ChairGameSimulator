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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Juego {

    
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
    private int velocity = 15;
    private int sillasIntocables = 0;
    private User userIntocable = null;
    private  static final String ANTIHORARIO="Antihorario";
    Stage windows;
//Desde el FXML
    @FXML
    Button btnPlay;
    @FXML
    Button btnStop;
    @FXML
    Pane spPane = new StackPane();
    boolean parar = false;                           //es el que controla el hilo, al estar en true el hilo de para.
    private Setting sett = new Setting(0);
    int posPlayer = -1;
    @FXML
    private MenuButton mbMusic;
    @FXML
    private Slider slVelocity;            //slider que controla la velocidad de los jugadores
    Media sound = new Media(new File("src/Files/Scatman.mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public Juego() {
        //constructor
    }

    @FXML
    void change(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Scatman");
        sound = new Media(new File("Files/Scatman.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }

    }

    @FXML
    void change1(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Morado");
        sound = new Media(new File("src/Files/Morado.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }
    }

    @FXML
    void change2(ActionEvent event) {
        mediaPlayer.stop();
        mbMusic.setText("Blinding Lights");
        sound = new Media(new File("src/Files/Blinding Lights.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (!parar) {
            mediaPlayer.play();
        }
    }

    @FXML
    void btnDirectionClicked() {
        changeDirection();
        tda.ListIterator<User> it = listUsersGame.iterator();
        while (it.limit()) {
            User u = it.next();
            u.setSentido(!u.isSentido());
        }
    }

    /**
     * Cuando se presiona el botón stop el hilo se interrumpirá.
     * En la linea 129 se pone lo que se quiere ejecutar en el tiempo
     * En la linea 131 recorre el mapa y los hace sentarse alrededor de las sillas
     * En la linea 135 establece cada cuanto tiempo se ejecutará la acción del run
     * En la linea 138 mientras la pila de sillas no esté vacía se
     * sigue ejecutando el hilo se interrumpe el hilo
     */
    @FXML
    protected void btnStopClicked() {
        mediaPlayer.stop();
        btnStop.setDisable(true);
        parar = true;
        season++;
        calcularDistancia();
        Thread hiloSentarse = new Thread(new Runnable() {
            public void run() {
                Runnable updater = () -> {
                    for (Map.Entry<Chair, User> entry : mapaDistancia.entrySet()) {
                        sentarJugadores(entry.getValue(), entry.getKey());
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(100);        
                        synchronized (this) {
                            while (pilaChairOccupated.isEmpty()) {
                                btnPlay.setDisable(false);
                                Thread.interrupted();       
                                wait();
                            }
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    Platform.runLater(updater);
                }
            }
        }
        );
        hiloSentarse.setDaemon(true);
        hiloSentarse.start();
    }

    /**
     * Define que se hará al presionar al botón play.
     * En la linea 172 se lo pone en false porque puede que antes se haya dado al botón stop.
     * En la linea  181 moverá las los jugadores en el tiempo aqui.
     * En la linea 184 en adelante se pone lo que se quiere ejecutar en el tiempo recorre la
     * lista de jugadores y los hace caminar alrededor de las sillas establece
     * cada cuanto tiempo se ejecutará la acción del run se interrumpe el hilo.
     */
    @FXML
    protected void btnPlayClicked() {
        if (season > 0) {
            this.changeDirection();
        }
        btnPlay.setDisable(true);
        btnStop.setDisable(false);
        mediaPlayer.play();
        parar = false;                                //
        if (season > 0) {
            this.spPane.getChildren().clear();
            this.saveChairsUser();
            this.organizeControllerChairs();
            this.organizeControllerUser();
        }

        this.mapaDistancia.clear();
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                Runnable updater = () -> {
                    for (int i = 0; i < listUsersGame.size(); i++) {
                        trazarCircunferencia(listUsersGame.get(i), 115);
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(velocity);       
                        synchronized (this) {
                            while (parar) {
                                Thread.interrupted();    
                                wait();
                            }
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    Platform.runLater(updater);
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    /**
     * Metodo initialize en cual servira para acomodar las sillas y los
     * jugadores segun la configuracion elegio el jugador. En Seeting crea la
     * lista de sillas accion
     * En la linea 234 incrementar la velocidad de los jugadores
     * En la linea 235 Se activa al notar un cambio si el slider marca más de 95 se fija en 2 la
     * velocidad porque puede dar un numero tan cercano a 0 ó 0 y el programa se caería. 
     * Guarda cada silla en una pila
     * @param nP valor del numero de participantes
     * @param numSillas numeros de sillas que son intocables
     * @param posPlayer
     * @param sentido el sentido en cual los jugadores se moveran en la primera
     * ronda.
     * @param stage envia la ventana de configuracion.
     */
    public void initialize(Double nP, Integer numSillas, int posPlayer, String sentido, Stage stage) {
        windows = stage;
        sillasIntocables = numSillas;
        sett = new Setting(nP, sentido);
        this.posPlayer = posPlayer;
        listChairs = sett.addChairs();
        listUsers = sett.addPlayers();
        this.slVelocity.valueProperty().addListener((observableValue, number, t1) -> {
            if (t1.intValue() > 95) {
                velocity = 2;
            } else {
                velocity = 40 - Math.round(40 * (t1.floatValue() / 100));
            }
        });
        for (int i = 0; i < listChairs.size(); i++) {
            listChairP.push(listChairs.get(i));
        }
        for (int i = 0; i < listUsers.size(); i++) {
            pileUsers.push(listUsers.get(i));
        }
        organizeControllerChairs();
        organizeControllerUser();
    }

    /**
     * Hace que el jugador se muevan en circulo. Sentido true= se mueve hacia la
     * derecha, sentido= false: se mueve hacia la izquierda
     *
     * @param user escoge el usuario que tocara moverse
     * @param initialRadio da el radio inicial del movimiento
     */
    public void trazarCircunferencia(User user, double initialRadio) {
        if (sett.getDirection().equalsIgnoreCase(ANTIHORARIO)) {
            recorrerSentido(user, initialRadio, true);

        } else if (sett.getDirection().equalsIgnoreCase("Horario")) {
            recorrerSentido(user, initialRadio, false);
        }
    }

    /**
     * Nos dice si se llegó al final de lo permitido en el eje X; si es true se
     * seguirá recorriendo hacia la derecha del eje 
     * En la linea 279 verifica si se llegó al X MAXIMO positivo
     * En la linea 281 verifica si se llegó al MÍNIMO negativo
     * @param user el usario que cambiara de sentido.
     * @param posX la posicion actual en X del usuario.
     * @param radio Radio del circulo.
     */
    public void sentido(User user, double posX, double radio) {
        if (posX + 1 > radio) {
            user.setSentido(false);
        } else if (posX - 1 < -radio) {
            user.setSentido(true);
        }
    }

    /**
     * Organiza las sillas de manera circular mediante la siguiente ecuacion.
     * (x)^2 +(y)^2 = 50^2 Lo que hace este metodo es recorrer en X desde la
     * posicion -radio hasta +radio para colocar los elementos en la parte
     * superior de la circuferencia usando la raiz positiva para encontrar Y,
     * para ir avanzando en X se calculo una distancia con el radio*4 y eso
     * dividido para el numero de sillas que se van a colocar, cuando las sumas
     * en X sean mayor al +radio, se procede a quitar a comenzar desde +radio
     * menos la distancia, dicho esto ahora X ira de +radio a -radio. Ahora
     * colocara las sillas en la parte inferior de la circuferencia con la raiz
     * negativa de la circugferencia para encontrar Y.
     */
    private void organizeControllerChairs() {
        double distance = (50f * 4) / listChairs.size();
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

    /**
     * Organiza los usuarios de manera circular mediante la siguiente ecuacion.
     * (x)^2 +(y)^2 = 50^2 Lo que hace este metodo es recorrer en X desde la
     * posicion -radio hasta +radio para colocar los elementos en la parte
     * superior de la circuferencia usando la raiz positiva para encontrar Y,
     * para ir avanzando en X se calculo una distancia con el radio*4 y eso
     * dividido para el numero de usuarios que se van a colocar, cuando las
     * sumas en X sean mayor al +radio, se procede a quitar a comenzar desde
     * +radio menos la distancia, dicho esto ahora X ira de +radio a -radio.
     * Ahora colocara los usuarios en la parte inferior de la circuferencia con
     * la raiz negativa de la circugferencia para encontrar Y.
     */
    private void organizeControllerUser() {
        double distance = (115f * 4) / listUsers.size();
        double inicio;
        boolean val1 = true;
        boolean val2 = true;
        while (!this.pileUsers.isEmpty()) {
            inicio = -115;
            while (val1) {
                double cordY = -(Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2)));
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateX(inicio);
                this.pileUsers.peek().setPosX(inicio);
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateY(cordY);
                this.pileUsers.peek().setPosY(cordY);
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().setSeated(false);
                this.pileUsers.peek().setSentido(true);
                listUsersGame.addLast(pileUsers.peek());
                spPane.getChildren().addAll(this.pileUsers.pop().getImage());
                inicio = inicio + distance;
                if (inicio > 115) {
                    val1 = false;
                    inicio = 115 - (distance / 2);
                }
            }
            while (val2 && !this.pileUsers.isEmpty()) {
                double cordy = (Math.sqrt(Math.pow(115, 2) - Math.pow(inicio, 2)));
                this.pileUsers.peek().getImage().setTranslateX(inicio);
                this.pileUsers.peek().setPosX(inicio);
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().getImage().setTranslateY(cordy);
                this.pileUsers.peek().setPosY(cordy);
                assert this.pileUsers.peek() != null;
                this.pileUsers.peek().setSeated(false);
                this.pileUsers.peek().setSentido(false);
                listUsersGame.addLast(pileUsers.peek());
                spPane.getChildren().addAll(this.pileUsers.pop().getImage());
                inicio = inicio - distance;
                if (inicio <= (-115)) {
                    val2 = false;
                }
            }
        }

    }

    /**
     * Calcula la distancia minima de cada silla con los jugadores y los agrega
     * En las linea 413 hasta la linea 427 al mapa recorre la lista de sillas recorre la lista de users Verifica si
     * el usuario no se a sentado aún guarda las posiciones x,y de la silla y
     * los users agrega la silla a la pila de sillas guarda las posiciones x,y
     * De la linea 428 hasta la 444 la silla y los users calcula la distancia minima entre dos puntos:
     * raiz((X-x)^2 + (Y-y)^2)) pone el estado del user en sentado para que no
     * En la liena 430 se vuelva a consultar}
     * En la linea 441 en adelante agrega la silla a la pila de sillas agrega la silla
     * y el usuario al mapa
     */
    public void calcularDistancia() {
        boolean existeIntocable = false;
        int sentado = 0;
        if (posPlayer >= 0) {
            existeIntocable = true;
        }
        for (int i = 0; i < listChairsGame.size() - sillasIntocables; i++) {
            double distanciaMinima = 1000000000;
            if (existeIntocable) {
                if (userIntocable == null) {
                    userIntocable = listUsersGame.get(posPlayer);
                }
                userIntocable.setSeated(true);
                mapaDistancia.put(listChairsGame.get(i), userIntocable);
                pilaChairOccupated.push(listChairsGame.get(i));                         //
                existeIntocable = false;
            } else {
                for (int j = 0; j < listUsersGame.size(); j++) {
                    if (!listUsersGame.get(j).isSeated()) {
                        double posXChair = listChairsGame.get(i).getPosX();
                        double posYChair = listChairsGame.get(i).getPosY();
                        double posXUser = listUsersGame.get(j).getPosX();
                        double posYUser = listUsersGame.get(j).getPosY();
                        double distancia = Math.sqrt((Math.pow(posXChair - posXUser, 2) + Math.pow(posYChair - posYUser, 2))); 
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
                listUsersGame.get(sentado).setSeated(true);                                
                pilaChairOccupated.push(listChairsGame.get(i));                         
                mapaDistancia.put(listChairsGame.get(i), pilaUserSentado.pop());         
            }

        }
    }

    /**
     * Guarda la informacion de cada silla y de cada usuario por cada ronda.
     */
    public void saveChairsUser() {
        this.listUsers.clear();
        this.listUsersGame.clear();
        this.listChairs.clear();
        this.listChairsGame.clear();
        for (Map.Entry<Chair, User> entry : mapaDistancia.entrySet()) {
            this.listUsers.addLast(entry.getValue());
            this.listChairs.addLast(entry.getKey());
        }
        for (int i = 0; i < listUsers.size(); i++) {
            pileUsers.push(listUsers.get(i));
        }
        for (int i = 0; i < listChairs.size(); i++) {
            listChairP.push(listChairs.get(i));
        }
        this.listChairs.removefirst();
        this.listChairP.removeLast();
    }

    //
    /**
     * Efectúa la animación de sentar al jugador; se utilizará la ecuación de la
     * En la linea 499 recta Y - y = m (X - x) para que el user "camine" en línea recta
     * En la linea 498 comprueba que la silla no esté ya ocupada (porque el hilo ejecuta la
     * acción para todos, por eso se debe verificar) se obtiene la pendiente de
     * En la linea 500 la recta verifica que el user no haya llegado a la silla si la pos X del
     * user está despues de la posX de la silla, disminuye la posX y con ella se
     * En la linea 513 obtiene la posY se calcula la posY si la pos X del user está antes de la
     * posX de la silla, aumenta la posX y con ella se obtiene la posY se
     * En la linea 518 calcula la posY se establece las posiciones en los atributos del user y
     * En la linea 527 se lo mueve si ya se llegó a la silla, se la marca como ocupada y se la
     * En la linea 529 elimina de la pila
     *
     * @param user
     * @param chair
     */
    public void sentarJugadores(User user, Chair chair) {
        btnPlay.setDisable(true);
        double posX = 0;
        if (!chair.isOccupated()) {
            double pendiente = ((chair.getPosY() - user.getPosY()) / (chair.getPosX() - user.getPosX()));  
            if (user.getPosX() != chair.getPosX() || user.getPosY() != chair.getPosY()) {
                double posY = 0;
                if (chair.getPosX() == user.getPosX()) {
                    double aumento = Math.abs((user.getPosY() - chair.getPosY()) / 23);
                    if (user.getPosY() > 0) {
                        posY = user.getPosY() - aumento;
                    } else {
                        posY = user.getPosY() + aumento;
                    }
                } else if (user.getPosX() > chair.getPosX()) {            
                    double aumento = Math.abs((user.getPosX() - chair.getPosX()) / 23);
                    posX = user.getPosX() - aumento;
                    user.setPosX(posX);
                    posY = pendiente * (posX - chair.getPosX()) + chair.getPosY();       
                } else if (user.getPosX() < chair.getPosX()) {     
                    double aumento = Math.abs((user.getPosX() - chair.getPosX()) / 23);
                    posX = user.getPosX() + aumento;
                    user.setPosX(posX);
                    posY = pendiente * (posX - chair.getPosX()) + chair.getPosY();  
                }
                user.setPosX(posX);
                user.setPosY(posY);
                user.getImage().setTranslateX(posX);
                user.getImage().setTranslateY(posY);
            }
            if (Math.abs(user.getPosX() - chair.getPosX()) < 4 && Math.abs(user.getPosY() - chair.getPosY()) < 4) {
                chair.setOccupated(true);
                pilaChairOccupated.pop();
                if (pilaChairOccupated.isEmpty()) {
                    comprobarGanador();
                }
            }

        }
    }

    public void comprobarGanador() {
        if (mapaDistancia.size() == 1 || mapaDistancia.size() - 1 <= sillasIntocables) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje de aviso");
            alert.setContentText("Juego terminado");
            alert.setHeaderText(null);
            alert.show();
            alert.setOnCloseRequest(e -> {
                windows.close();
                this.closeWindows();
            });
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

    public void changeDirection() {

        if (sett.getDirection().equalsIgnoreCase(ANTIHORARIO)) {
            sett.setDirection("Horario");
        } else {
            sett.setDirection(ANTIHORARIO);
        }

    }

    
    /**
     * Hace que los jugadores se muevan según la dirección dada; true para
     * en la linea 607 sentido antihorario} verifica que se esté recorriendo hacia la derecha en
     * En la linea 608 el eje X verifica si se llegó al final de lo permitido en X comprueba si
     * En la linea 609 se mueve hacia la derecha o la izquierda de las X ecuación de la
     * En la linea 610 circunferencia aumentamos la posición X Sino se cumple lo anterior, se
     * En la linea 611 tomará la parte negativa de la raíz cuadrada de la ecuación guarda la
     * En la linea 615 y 616 cambian posición x,y en el usuario
     * @param user
     * @param initialRadio
     * @param direction
     */
    public void recorrerSentido(User user, double initialRadio, boolean direction) {
        double posX = user.getPosX();
        double posY;
        int signoA = -1;
        int signoB = 1;
        if (direction) {
            signoA = 1;
            signoB = -1;
        }
        sentido(user, posX, initialRadio);      
        if (user.isSentido()) {
            posY = signoA * Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow((posX), 2)));    
            posX += 1;                                                        
        } else {
            posY = signoB * Math.sqrt(Math.abs(Math.pow(115, 2) - Math.pow(posX, 2)));
            posX -= 1;
        }
        user.setPosX(posX);
        user.setPosY(posY);                                                       
        user.getImage().setTranslateX(posX);
        user.getImage().setTranslateY(posY);
    }
}
