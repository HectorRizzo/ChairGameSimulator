/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piece;

import javafx.scene.image.ImageView;



/**
 *
 * @author i7
 */
public class Chair {
    private final ImageView image;
    double posX;
    double posY;
    boolean occupated=false;

    public Chair(ImageView image) {
        this.image = image;
    }

    public Chair(ImageView image, double posX, double posY) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.occupated=false;
    }

    //getters & setters
    public ImageView getImage() {
        return image;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setOccupated(boolean occupated) {
        this.occupated = occupated;
    }

    public boolean isOccupated() {
        return occupated;
    }

}
