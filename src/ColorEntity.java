import java.awt.*;
import java.util.Objects;

public class ColorEntity implements Comparable<ColorEntity> {
    private int red ;
    private int green ;
    private int blue ;
    private int rgbVal ;

    public Integer getAppears() {
        return appears;
    }

    public void setAppears(Integer appears) {
        this.appears = appears;
    }

    private Integer appears ;
    public int getRgbVal() {
        return rgbVal;
    }

    public void setRgbVal(int rgbVal) {
        this.rgbVal = rgbVal;
    }

    public ColorEntity(){

    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    @Override
    public String toString() {
        return "ColorEntity{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", rgbVal=" + rgbVal
                +", appears="+appears+'}';
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    public ColorEntity(int rgb){
        this.rgbVal = rgb ;
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
}
