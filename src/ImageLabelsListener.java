import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ImageLabelsListener implements MouseListener {
    ImageChooser frame ;
    public ImageLabelsListener(ImageChooser frame) {
        this.frame = frame;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JLabel label = (JLabel)mouseEvent.getSource() ;
        try {
            ImageSingleton.getInstance().setCurrentImageAddress(label.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.findCurrentImageIndex() ;
        frame.setVisible(false);
        try {
            ImageCanvas imageCanvas = new ImageCanvas(frame) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        JLabel label = (JLabel)mouseEvent.getSource() ;
        label.setForeground(Color.BLUE);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        JLabel label = (JLabel)mouseEvent.getSource() ;
        label.setForeground(Color.BLACK);
    }
}
