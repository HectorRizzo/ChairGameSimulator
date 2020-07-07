/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 *
 * @author i7
 */
public class Scene2 {
  private VBox root = new VBox();
  private Button bt = new Button("Bien");

    public Scene2() {
        Organizar();
    }
    private void Organizar(){
        getRoot().getChildren().add(bt);
    }

    public VBox getRoot() {
        return root;
    }
    
}
