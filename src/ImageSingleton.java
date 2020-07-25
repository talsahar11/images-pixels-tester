import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSingleton {
    public static ImageSingleton instance ;
    private BufferedImage currentImage ;
    private String currentImageAddress;
    public ImageSingleton() {

    }
    static {
        instance = new ImageSingleton() ;
    }
    public static ImageSingleton getInstance(){
        return instance ;
    }
    public BufferedImage getCurrentImage(){
        return currentImage ;
    }
    public void setCurrentImage(BufferedImage image){
        this.currentImage = image ;
    }
    public void setCurrentImageAddress(String currentImageAddress) throws IOException {
        this.currentImageAddress = currentImageAddress ;
        this.setCurrentImage(ImageIO.read(new File(currentImageAddress)));
    }
    public String getCurrentImageAddress(){
        return currentImageAddress ;
    }
}
