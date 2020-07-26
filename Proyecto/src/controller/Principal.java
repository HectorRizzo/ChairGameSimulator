/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jocelyn Chicaiza
 */
public class Principal implements Initializable {
    @FXML
    void toExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void toPlay(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/View/Configurations.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showHelp(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
             FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/Help.fxml") );
            Parent parent = loader.load();
            Help controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e->controller.closeWindows());
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Meotodo para controllar los elemetos de la vista
    }
}
