/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import piece.User;
import tda.LCDE;


/**
 *
 * @author Jocelyn Chicaiza
 */
public class Configurations implements Initializable {

    LCDE<Image> listImagePlayer= new LCDE<>();
    private tda.ListIterator <Image> it;
    int playerChoosed=-1;
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
    private ChoiceBox <Integer> chbNumSillas;
    @FXML
    Label pnum;
    @FXML
    private ToggleGroup group;
    @FXML
    private ImageView imvPlayer;
    @FXML
    private Button nextPlayer;
    @FXML
    private Button choosePlayer;
    @FXML
    void jugar(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        RadioButton select = (RadioButton) group.getSelectedToggle();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/View/Juego.fxml") );
        Parent parent = loader.load();
        Juego controller = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
         controller.initialize(Double.parseDouble(pnum.getText()), chbNumSillas.getValue(), playerChoosed,select.getText(),stage);
        stage.show();
        stage.setOnCloseRequest(e->controller.closeWindows());
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addImagePlayer();
        it= listImagePlayer.iterator();
        it.next();
        this.chbNumSillas.setItems(setItemsList());
        chbNumSillas.setValue(0);
        this.slPersonas.valueProperty().addListener((observable, oldValue, newValue) -> pnum.setText(String.valueOf(newValue)));

    }
    @FXML
    void choosePlayerClicked(){
        tda.ListIterator <Image> itPlayer=listImagePlayer.iterator();
        boolean encontrado=true;
        int h=0;
        while(itPlayer.limit()&&encontrado){
            if(itPlayer.next()==imvPlayer.getImage()){
                playerChoosed=h;
                encontrado=true;
            }
            h++;
        }


    }


    protected ObservableList<Integer> setItemsList(){
        ObservableList <Integer> obList= FXCollections.observableArrayList();
        obList.addAll(0,1,2);
        return obList;
    }

    @FXML
    void nextPlayerClicked(){
        Image im= it.next();
        imvPlayer.setImage(im);


    }
    protected void addImagePlayer(){
        int i= 0;
        while (i<=7){
            Image image = new Image("/Files/usuario"+i+".png");
            listImagePlayer.addLast(image);
            i++;
        }
        imvPlayer.setImage(listImagePlayer.get(0));
    }

}
