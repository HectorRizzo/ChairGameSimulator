package principal;


import javafx.scene.image.ImageView;


public class usuario {
   
    private ImageView image;
    private boolean seated;
    private double posX;
    private double posY;

    public usuario() {
        image = new ImageView("usuario.jpg");
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
