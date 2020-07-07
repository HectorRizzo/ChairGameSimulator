package principal;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.Game;
import GUI.Scene1;
import TDA.LCDE;
import TDA.ListDoubleC;
import TDA.ListIterator;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Xavier
 */
public class Proyecto extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Scene scene = new Scene(new Game().getRoot() , 600, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {

        launch();
        /*
        ListDoubleC <ImageView> prueba1= new ListDoubleC<>();
        File file = new File("C:/Users/i7/Desktop/Espol/Estructura de Datos/estructura/Proyecto/src/Files/silla.jpg");
        Image image = new Image(file.toURI().toString());
        ImageView imv = new ImageView(image);
        prueba1.addFirst(imv);
        System.out.println("hello");
        */  
    }
    
}
