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

    public Chair(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    
}
