/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;



/**
 *
 * @author i7
 */
public class Chair implements Comparable<Chair>{
    private ImageView image;
    double pos_X;
    double pos_y;
    boolean occupated=false;

    public Chair(ImageView image) {
        this.image = image;
    }

    public Chair(double pos_X, double pos_y) {
        this.pos_X = pos_X;
        this.pos_y = pos_y;
    }

    public Chair(ImageView image, double pos_X, double pos_y) {
        this.image = image;
        this.pos_X = pos_X;
        this.pos_y = pos_y;
        this.occupated=false;
    }
    
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public double getPos_X() {
        return pos_X;
    }

    public void setPos_X(double pos_X) {
        this.pos_X = pos_X;
    }

    public double getPos_y() {
        return pos_y;
    }

    public void setPos_y(double pos_y) {
        this.pos_y = pos_y;
    }

    public void setOccupated(boolean occupated) {
        this.occupated = occupated;
    }

    public boolean isOccupated() {
        return occupated;
    }

    @Override
    public int compareTo(Chair o) {
        if(this.getPos_X()>o.getPos_X()){
            return 1;
        }if(this.getPos_X()<o.getPos_X()){
            return -1;
        }
        return 0;
    }
}
