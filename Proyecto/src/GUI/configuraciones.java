/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Piece.Setting;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 *
 * @author Jocelyn Chicaiza
 */
public class configuraciones implements Initializable {

    @FXML
    Pane principal = new Pane();
    @FXML
    private Label lbRotacion;
    @FXML
    private Button btnPlay;
    @FXML
    private Label lbPersonas;
    @FXML
    private Slider slPersonas;
    @FXML
    private RadioButton rbHorario;

    @FXML
    private RadioButton rbAntihorario;
    @FXML
    private Label nPersonas;


    @FXML
    void jugar(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        /*
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "Juego.fxml"
                )
        );
        Parent parent = loader.load();
        */
        
        Parent parent =  FXMLLoader.load(getClass().getResource("Juego_1.fxml"));
        /*
        Setting set = new Setting(slPersonas.getValue());
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                       "Juego.fxml"
                ));
        Juego controller =loader.<Juego>getController();
        controller.iniData(set);
*/
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
