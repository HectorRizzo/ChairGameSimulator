/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;
import javafx.scene.image.ImageView;



/**
 *
 * @author i7
 */
public class Chair implements Comparable<Chair>{
    private ImageView image;
    double posX;
    double posY;
    boolean occupated=false;

    public Chair(ImageView image) {
        this.image = image;
    }

    public Chair(ImageView image, double posX, double posy) {
        this.image = image;
        this.posX = posX;
        this.posY = posy;
        this.occupated=false;
    }

    //getters & setters
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setOccupated(boolean occupated) {
        this.occupated = occupated;
    }

    public boolean isOccupated() {
        return occupated;
    }

    @Override
    public int compareTo(Chair o) {
        if(this.getPosX()>o.getPosX()){
            return 1;
        }else if(this.getPosX()<o.getPosX()){
            return -1;
        }
        return 0;
    }
}
