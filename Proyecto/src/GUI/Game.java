/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Piece.Chair;
import Piece.Setting;
import TDA.LCDE;
import TDA.ListDoubleC;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author i7
 */
public class Game {

    private BorderPane root = new BorderPane();
    private Pane paneButtons = new Pane();
    private Button play = new Button(" Play ");
    private Button stop = new Button(" Stop ");
    private ListDoubleC listPlayers = new ListDoubleC();
    private LCDE<Chair> listChairs = new LCDE();
    private Setting seti = new Setting(5);
    private Pane detail = new Pane ();
    //private BorderPane paneChair = new BorderPane();

    public Game() {
        setListChairs();
        System.out.println(seti.getListChairs().size());

        Organizar();

    }
    public void setListChairs() {
        seti.addChairs();
        this.listChairs = seti.getListChairs();
    }

    public void Organizar() {
        
        for(int i = 0 ; i<listChairs.size();i++){ 
            listChairs.get(i).getImage().setLayoutX(i);
            listChairs.get(i).getImage().setLayoutY(i*30+15);
            detail.getChildren().addAll( listChairs.get(i).getImage());
        }
        play.setLayoutX(150);
        play.setLayoutY(100);
        stop.setLayoutX(400);
        stop.setLayoutY(100);
        paneButtons.getChildren().addAll(play, stop);
        root.setTop(paneButtons);
        Rectangle rectBottom = new Rectangle(150, 400);
        rectBottom.setFill(Color.AQUA);
        Rectangle rectUpper = new Rectangle(150, 400);
        rectUpper.setFill(Color.CORAL);
        Rectangle rectMiddle = new Rectangle(600, 100);
        rectMiddle.setFill(Color.CADETBLUE);
        root.setLeft(rectUpper);
        root.setRight(rectBottom);
        root.setCenter(detail);
        root.setBottom(rectMiddle);

    }

    public BorderPane getRoot() {
        return root;
    }

}
