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
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author Xavier
 */
public class Proyecto extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        this.primaryStage = primaryStage;
        initRootLayaut();

    }

    public void initRootLayaut() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/GUI/principal.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
