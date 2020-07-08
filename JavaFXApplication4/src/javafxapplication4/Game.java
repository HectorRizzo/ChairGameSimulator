/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import Piece.Chair;
import Piece.Setting;
import TDA.LCDE;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author i7
 */
public class Game {
    @FXML private Pane PaneChair;
    @FXML private BorderPane root;
    private LCDE<Chair> listChairs = new LCDE();
    private Setting seti = new Setting(5);
    public Game() {
    }
    
   public void initialize() {
    setListChairs();
  }
    public void setListChairs() {
        seti.addChairs();
        this.listChairs = seti.getListChairs();
    }

    public void Relleno(){
        for(int i = 0 ; i<listChairs.size();i++){ 
            Random rd = new Random();
            listChairs.get(i).getImage().setLayoutX(rd.nextInt(100)*i);
            listChairs.get(i).getImage().setLayoutY(rd.nextInt(100)*i);
            PaneChair.getChildren().addAll( listChairs.get(i).getImage());
        }
        root.setCenter(PaneChair);
    }
    
}
