/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author Xavier
 */
public class Proyecto extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println(getClass().getResource("Juego.fxml"));
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("Juego.fxml"));
        Parent root= loader.load();
        Scene sce= new Scene(root);
        primaryStage.setScene(sce);
        primaryStage.show();
        //   Parent root= FXMLLoader.load(getClass().getResource("Juego.fxml"));

    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {

        launch(args);

        }

    
}
