import java.awt.*;
import java.util.Objects;

public class ColorEntity implements Comparable<ColorEntity> {
    private int red ;
    private int green ;
    private int blue ;
    private int rgbVal ;
    private float h, s, b ;
    private Integer appears ;
    private float[] hsbValues = new float[3] ;

    public ColorEntity(int red, int green, int blue){
        this.rgbVal = new Color(red,green,blue).getRGB();
        maintainAllColorValuesUpdated(rgbVal);
    }

    public ColorEntity(int rgbVal){
        maintainAllColorValuesUpdated(rgbVal);
    }

    public void maintainAllColorValuesUpdated(int rgbVal) {
        Color color = new Color(rgbVal) ;
        this.rgbVal = rgbVal ;
        this.red = color.getRed();
        this.green = color.getGreen() ;
        this.blue = color.getBlue() ;
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),hsbValues) ;
    }

    public void setAppears(Integer appears) {
        this.appears = appears;
    }
    public Integer getAppears() {
        return appears;
    }

    public int getRgbVal() {
        return rgbVal;
    }
    public void setRgbVal(int rgbVal) {
        maintainAllColorValuesUpdated(rgbVal);
    }

    public int getRed() {
        return red;
    }
    public void setRed(int red) {
        Color color = new Color(red, green,blue) ;
        maintainAllColorValuesUpdated(color.getRGB());
    }

    public int getGreen() {
        return green;
    }
    public void setGreen(int green) {
        Color color = new Color(red, green,blue) ;
        maintainAllColorValuesUpdated(color.getRGB());
    }

    public int getBlue() {
        return blue;
    }
    public void setBlue(int blue) {
        Color color = new Color(red, green,blue) ;
        maintainAllColorValuesUpdated(color.getRGB());
    }

    public float[] getHsbValues(){
        return hsbValues ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorEntity that = (ColorEntity) o;
        return rgbVal == that.rgbVal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rgbVal);
    }

    @Override
    public int compareTo(ColorEntity colorEntity) {
        if(this.getRgbVal() > colorEntity.getRgbVal()) {
            return 1 ;
        }
        if(this.getRgbVal() < colorEntity.getRgbVal()){
            return -1 ;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "ColorEntity Values: " +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", rgbVal=" + rgbVal
                + ", Hue=" +hsbValues[0] +", Saturation=" + hsbValues[1] + ", Brightness=" + hsbValues[2];
    }
}
