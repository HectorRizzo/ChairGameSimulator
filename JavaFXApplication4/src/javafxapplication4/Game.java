/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import Piece.Chair;
import Piece.Setting;
import TDA.LCDE;
import java.util.ArrayDeque;
import java.util.Deque;
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
    private Setting seti = new Setting(6);
    private LCDE <Chair> listChairsGame = new LCDE<>();
    private Deque<Chair> listChairP = new ArrayDeque<>();
    
   
    
   public void initialize() {
    setListChairs();
     Relleno();
  }
    public void setListChairs() {
        listChairs=seti.addChairs();
        for(int i=0; i<listChairs.size();i++){
            listChairP.push(listChairs.get(i));
        }
    }
    //(x-300)^2 +(y-200)^2 = 50^2
    public void Relleno(){
        int DobDiam = 200;
        int distance = DobDiam / listChairs.size();
        boolean val1=true;
        boolean val2=true;
        int inicio = 250;
        int i =0;
        /*
        for (int inicio = 200; i < 400; i = i + distance) {
            double coor_y = (Math.sqrt(Math.pow(100, 2) - Math.pow(i - 300, 2))) + 200;
            listChairs.get(indice).getImage().setLayoutX(i);
            listChairs.get(indice).getImage().setLayoutY(coor_y);
            if (indice == 0) {
                listChairsGame.addFirst(new Chair(i, coor_y));
            } else {
                listChairsGame.addLast(new Chair(i, coor_y));
            }
            PaneChair.getChildren().addAll(listChairs.get(indice).getImage());
            indice++;
        }
        for (int i = 400; i >200; i = i - distance) {
            double coor_y = -(Math.sqrt(Math.pow(100, 2) - Math.pow(i - 300, 2))) + 200;
            listChairs.get(indice).getImage().setLayoutX(i);
            listChairs.get(indice).getImage().setLayoutY(coor_y);
            listChairsGame.addLast(new Chair(i, coor_y));
            PaneChair.getChildren().addAll(listChairs.get(indice).getImage());
            indice++;
        }

        while(i!=listChairs.size()){
            while(val1){
            double coor_y = (Math.sqrt(Math.pow(100, 2) - Math.pow(inicio - 300, 2))) + 200;
            listChairs.get(i).getImage().setLayoutX(inicio);
            listChairs.get(i).getImage().setLayoutY(coor_y);
            if (i == 0) {
                listChairsGame.addFirst(new Chair(inicio, coor_y));
            } else {
                listChairsGame.addLast(new Chair(inicio, coor_y));
            }
            PaneChair.getChildren().addAll(listChairs.get(i).getImage());
            inicio=inicio+distance;
            if(inicio>DobDiam){
                val1=false;
                inicio=DobDiam-distance;
                 i++;
            }else{
            i++;
            }
            }
            while(val2 && i!=listChairs.size()){
            double coor_y = -(Math.sqrt(Math.pow(100, 2) - Math.pow(inicio - 300, 2))) + 200;
            listChairs.get(i).getImage().setLayoutX(inicio);
            listChairs.get(i).getImage().setLayoutY(coor_y);
            listChairsGame.addLast(new Chair(inicio, coor_y));
            PaneChair.getChildren().addAll(listChairs.get(i).getImage());
            inicio=inicio-distance;
            if(inicio<=200){
                val2=false;
                i++;
            }else{
            i++;
            }
            }
            }
        */
        while(!this.listChairP.isEmpty()){
            while(val1){
            double coor_y = (Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 300, 2))) + 200;
            this.listChairP.peek().getImage().setLayoutX(inicio);
            this.listChairP.peek().getImage().setLayoutY(coor_y);
            if (i == 0) {
                listChairsGame.addFirst(new Chair(inicio, coor_y));
                i++;
            } else {
                listChairsGame.addLast(new Chair(inicio, coor_y));
            }
            PaneChair.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio+distance;
            if(inicio>350){
                val1=false;
                inicio=350-distance;
            }
            }
            while(val2 && !this.listChairP.isEmpty()){
            double coor_y = -(Math.sqrt(Math.pow(50, 2) - Math.pow(inicio - 300, 2))) + 200;
             this.listChairP.peek().getImage().setLayoutX(inicio);
             this.listChairP.peek().getImage().setLayoutY(coor_y);
            listChairsGame.addLast(new Chair(inicio, coor_y));
            PaneChair.getChildren().addAll(this.listChairP.pop().getImage());
            inicio=inicio-distance;
            if(inicio<=250){
                val2=false;
            }
            }
        }
        
        // Random rd = new Random();
        //listChairs.get(0).getImage().setLayoutX(300);
        //listChairs.get(0).getImage().setLayoutY(200);
        //PaneChair.getChildren().addAll( listChairs.get(0).getImage());
        root.setCenter(PaneChair);
    }
    
}
