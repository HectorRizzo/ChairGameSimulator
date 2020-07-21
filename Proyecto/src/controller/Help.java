/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import TDA.LCDE;
import TDA.ListIterator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author i7
 */
public class Help {
    @FXML
    private Pane pnImages;
    @FXML
    private ImageView imvHelp;
    @FXML
    private Button previous;
    @FXML
    private Button next;
    
    private final LCDE<Image> listImage = new LCDE<>();
    private ListIterator<Image> it;
    public void initialize(){
        listImage.addLast(new Image("/Files/sc1.png"));
        listImage.addLast(new Image("/Files/sc2.png"));
        imvHelp.setImage(listImage.get(0));
        it= listImage.iterator();
    }
    @FXML
    protected void btnBackClicked(){
        imvHelp.setImage(it.previous());
    }
    @FXML
    protected void btnNextClicked(){
        imvHelp.setImage(it.next());
    }
    
    public void closeWindows(){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Principal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
             Stage st = new Stage();
            st.setScene(scene);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(Configurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
