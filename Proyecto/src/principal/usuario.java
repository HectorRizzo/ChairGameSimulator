package principal;


import javafx.scene.image.ImageView;


public class usuario {
    private ImageView image;
    private boolean seated;

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
    
    
}
