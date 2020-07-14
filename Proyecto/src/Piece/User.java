package Piece;


import javafx.scene.image.ImageView;


public class User implements Comparable<User>{
   
    private ImageView image;
    private boolean seated;
    private double posX;
    private double posY;
    private boolean sentido;                                //nos dice hacia que dirección en eje x se moverá; true= derecha, false= izquierda

    public User(ImageView image, boolean seated) {
        this.image = image;
        this.seated=seated;
    }

    public User(boolean seated, double posX, double posY) {
        this.seated = seated;
        this.posX = posX;
        this.posY = posY;
    }

    public User(ImageView image, boolean seated, double posX, double posY,boolean sentido) {
        this.image = image;
        this.seated = seated;
        this.posX = posX;
        this.posY = posY;
        this.sentido=sentido;
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

    public boolean isSentido() {
        return sentido;
    }

    public void setSentido(boolean sentido) {
        this.sentido = sentido;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    @Override
    public int compareTo(User o) {
        if(this.getPosX()>o.getPosX()){
            return 1;
        }
        if(this.getPosX()<o.getPosX()){
            return -1;
        }
        return 0;
    }
}
