/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Jocelyn Chicaiza
 */
public class principal implements Initializable {

    Pane principal = new Pane();
    @FXML
    private Button btnSalir;

    @FXML
    private Button btnJugar;

    @FXML
    private Button btnAyuda;
    @FXML
    private ImageView img;

    @FXML
    void Salir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void jugar(ActionEvent event) throws IOException, Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/GUI/configuraciones.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void mostrarAyuda(ActionEvent event) {
        //Evento del boton ayuda 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
