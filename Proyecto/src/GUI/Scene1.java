/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author i7
 */
public class Scene1 {
    private VBox root = new VBox();
    private Button bt1 = new Button("Hola");
    private Button bt2 = new Button("Paul");
    private Button bt3 = new Button("Accede a la escena 2");
    private Stage st1 = new Stage();

    public Scene1(Stage st1) {
        this.st1=st1;
        OrganizarControles();
       // bt3.setOnKeyPressed();
    }
    private void OrganizarControles(){
       getRoot().getChildren().addAll(bt1,bt2,bt3);
       bt3.setOnAction(e -> ChangeScene());
    }
    private void ChangeScene(){
        Scene sc = new Scene(new Scene2().getRoot(),250,250);
        st1.setScene(sc);
        st1.show();
    }
    public VBox getRoot() {
        return root;
    }
    
}
