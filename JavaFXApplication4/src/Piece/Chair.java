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
public class Chair{
    private ImageView image;
    private double pos_X;
    private double pos_Y;

    public Chair(ImageView image) {
        this.image = image;
    }

    public Chair(double pos_X, double pos_Y) {
        this.pos_X = pos_X;
        this.pos_Y = pos_Y;
    }
    
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    
}
