package principal;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import TDA.LCDE;
import TDA.ListDoubleC;
import TDA.ListIterator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Xavier
 */
public class Proyecto extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {

       // launch(args);

        ListDoubleC <Integer> prueba1= new ListDoubleC<>();
        prueba1.addFirst(0);
        prueba1.addFirst(1);
        prueba1.addFirst(2);
        prueba1.addFirst(3);
        prueba1.addFirst(4);
        prueba1.addLast(22);
        ListIterator<Integer> it1= prueba1.Iterator();
        while(it1.Limit()){
        System.out.println(it1.previous());
        }
    }
    
}
