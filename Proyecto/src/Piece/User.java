package Piece;


import javafx.scene.image.ImageView;


public class User {
   
    private ImageView image;
    private boolean seated;
    private double posX;
    private double posY;

    public User(ImageView image, boolean seated) {
        this.image = image;
        this.seated=seated;
    }

    public User(boolean seated, double posX, double posY) {
        this.seated = seated;
        this.posX = posX;
        this.posY = posY;
    }

    public User(ImageView image, boolean seated, double posX, double posY) {
        this.image = image;
        this.seated = seated;
        this.posX = posX;
        this.posY = posY;
    }
    
    
    public boolean isSeated(){
        return seated;
    }

    public ImageView getImage() {
        return image;
    }

    public void setIamgen(ImageView image) {
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
    
    
}
