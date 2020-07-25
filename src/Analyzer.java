import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Analyzer {
    private BufferedImage image ;
    private int x, y, width, height ;
    private Rectangle rectangle ;
    private Set<ColorEntity> colors ;
    private int[][][] rgbArr ;
    public Analyzer(BufferedImage image, Rectangle rectangle){
        this.image = image ;
        this.rectangle = rectangle;
        this.colors = new HashSet<>() ;
        this.rgbArr = new int[256][256][256] ;
    }
    public void scanRect() throws Exception {
        int currentRgb;
        ColorEntity currentColor;
        Color rgbColor;
        for (int i = rectangle.x; i < rectangle.x + rectangle.width; i++) {
            for (int j = rectangle.y; j < rectangle.y + rectangle.height; j++) {
                currentColor = new ColorEntity();
                currentRgb = image.getRGB(i, j);
                rgbColor = new Color(currentRgb);
                currentColor.setRed(rgbColor.getRed());
                currentColor.setGreen(rgbColor.getGreen());
                currentColor.setBlue(rgbColor.getBlue());
                currentColor.setRgbVal(currentRgb);
                rgbArr[rgbColor.getRed()][rgbColor.getGreen()][rgbColor.getBlue()]++ ;
                currentColor.setAppears(1);
                colors.add(currentColor);

            }
        }
        int appears ;
        List<ColorEntity> filteredList = new ArrayList<>() ;
        for (ColorEntity color : colors) {
            appears = rgbArr[color.getRed()][color.getGreen()][color.getBlue()] ;
            color.setAppears(appears);
//            if(appears>1){
                filteredList.add(color);
//            }
        }

        ColorEntity[] colorsArray = colors.toArray(ColorEntity[]::new) ;
        SpectrumOfColors spectrumOfColors = new SpectrumOfColors(filteredList.toArray(ColorEntity[]::new)) ;
        System.out.println("-------END----------" + colors.toArray().length + " box size: " + rectangle.height*rectangle.width);

    }

}
