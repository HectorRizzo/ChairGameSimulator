/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

        LCDE <Integer> lista= new LCDE<>();

        for(int i=1; i<6;i++){
            lista.addLast(i);
        }
        System.out.println(lista.size());
        System.out.println(lista.toString());

        System.out.println(lista.remove(3));
        System.out.println(lista.remove(1));
        System.out.println(lista.toString());
        System.out.println(lista.size());
        lista.clear();
        System.out.println(lista);
        System.out.println(lista.size());

    }
    
}
