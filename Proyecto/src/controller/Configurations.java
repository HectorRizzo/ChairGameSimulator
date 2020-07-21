/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author Jocelyn Chicaiza
 */
public class Configurations implements Initializable {

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
    private Label nPersonas;
    @FXML
    Label pnum;
    @FXML
    private ToggleGroup group;

    @FXML
    void jugar(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        RadioButton select = (RadioButton) group.getSelectedToggle();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/View/Juego.fxml") );
        Parent parent = loader.load();
        Juego controller = loader.getController();
        controller.initialize(Double.parseDouble(pnum.getText()), select.getText());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
       

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.slPersonas.valueProperty().addListener((observable, oldValue, newValue) -> pnum.setText(String.valueOf(newValue)));

    }
}
