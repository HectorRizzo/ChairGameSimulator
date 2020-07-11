    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;


import TDA.LCDE;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author i7
 */
public class Setting <E> {
    private double numberParticipants;
    private String Direction;
    //private ListDoubleC <Player>listPlayers;
    private LCDE <Chair>listChairs= new LCDE<>();

    public Setting(double numberParticipants, String Direction) {
        this.numberParticipants = numberParticipants;
        this.Direction = Direction;
        this.listChairs=null;
       
    }

    public Setting(double numberParticipants) {
        this.numberParticipants = numberParticipants;
    }
    
    /*
    private boolean addPlayers(){
        for(int i =0; i< numberParticipants;i++){
            if(i==0){
                listPlayers.addFirst(new Player(0));
            }else{
                listPlayers.addLast(new Player(0)):
            }
        }
        return true;
    }
*/
    public LCDE<Chair> addChairs() {

        for (int i = 0; i < getNumberParticipants() - 1; i++) {
            if (i == 0) {
                File file = new File("C:/Users/i7/Desktop/Espol/Estructura de Datos/estructura/Proyecto/src/Files/silla.png");
                Image image = new Image(file.toURI().toString());
                ImageView imv = new ImageView(image);
                imv.setFitHeight(50);
                imv.setFitWidth(50);
                imv.setPreserveRatio(true);
                listChairs.addFirst(new Chair(imv));
            } else {
                File file = new File("C:/Users/i7/Desktop/Espol/Estructura de Datos/estructura/Proyecto/src/Files/silla.png");
                Image image = new Image(file.toURI().toString());
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
