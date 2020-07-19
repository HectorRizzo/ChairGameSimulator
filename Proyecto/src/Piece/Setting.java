    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;


import TDA.LCDE;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author i7
 */
public class Setting <E> {
    private double numberParticipants;
    private String Direction;
    private LCDE <User>listPlayers=new LCDE<>();
    private LCDE <Chair>listChairs= new LCDE<>();

    public Setting(double numberParticipants, String Direction) {
        this.numberParticipants = numberParticipants;
        this.Direction = Direction;
       
    }

    public Setting(double numberParticipants) {
        this.numberParticipants = numberParticipants;
    }
    
    
    public LCDE<User> addPlayers() {
        for(int i =0; i< getNumberParticipants();i++){
            if(i==0){
                Image image = new Image("/Files/usuario1.png");
                ImageView imv = new ImageView(image);
                imv.setFitHeight(65);
                imv.setFitWidth(65);
                imv.setPreserveRatio(true);
                listPlayers.addFirst(new User(imv,false));
            }else{
                //File file = new File("@../Files/usuario.png");
                Image image = new Image("/Files/usuario.png");
                ImageView imv = new ImageView(image);
                imv.setFitHeight(65);
                imv.setFitWidth(65);
                imv.setPreserveRatio(true);
                listPlayers.addLast(new User(imv,false));
            }
        }
        return listPlayers;
    }   

    public LCDE<Chair> addChairs() {

        for (double i = 0; i < getNumberParticipants() - 1; i++) {
            if (i == 0) {
                //File file = new File("@../Files/silla.png");
                Image image = new Image("/Files/silla.png");
                ImageView imv = new ImageView(image);
                imv.setFitHeight(50);
                imv.setFitWidth(50);
                imv.setPreserveRatio(true);
                listChairs.addFirst(new Chair(imv));
            } else {
                //File file = new File("@../Files/silla.png");
                Image image = new Image("/Files/silla.png");
                ImageView imv = new ImageView(image);
                imv.setFitHeight(50);
                imv.setFitWidth(50);
                imv.setPreserveRatio(true);
                listChairs.addLast(new Chair(imv));
            }
        }
        return listChairs;
    }

    /**
     * @return the numberParticipants
     */
    public double getNumberParticipants() {
        return numberParticipants;
    }

    /**
     * @param numberParticipants the numberParticipants to set
     */
    public void setNumberParticipants(int numberParticipants) {
        this.numberParticipants = numberParticipants;
    }

    /**
     * @return the Direction
     */
    public String getDirection() {
        return Direction;
    }

    /**
     * @param Direction the Direction to set
     */
    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    /**
     * @return the listChairs
     */
    public LCDE  <Chair> getListChairs() {
        return listChairs;
    }

    /**
     * @param listChairs the listChairs to set
     */
    public void setListChairs(LCDE  <Chair> listChairs) {
        this.listChairs = listChairs;
    }
    
}
