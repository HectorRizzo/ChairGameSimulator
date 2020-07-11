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
    double pos_X;
    double pos_y;

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
    }
    
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    
}
