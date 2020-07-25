import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ImageChooser extends JFrame {
    private ImageCanvas imageCanvas ;
    private JTextField addressField ;
    public ImageChooser() throws IOException {
        JPanel centerPanel = new JPanel() ;
        JPanel topPanel = new JPanel() ;
        JPanel southPanel = new JPanel() ;
        JButton analyzeButton = new JButton("Analyze current values") ;

        addressField = new JTextField("C:\\Users\\alex\\Desktop\\ScreenSaverPictures") ;
        addressField.setPreferredSize(new Dimension(430,25));
        topPanel.add(addressField) ;

        this.setLayout(new BorderLayout());
//        centerPanel.setBackground(Color.GREEN);
        this.setSize(550, 700);
        this.getContentPane().add(topPanel, BorderLayout.NORTH) ;
        centerPanel = this.setCenterPanelWithImagesFiles(centerPanel) ;
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    private List<File> jpgFiles = new ArrayList<>() ;
    public JPanel setCenterPanelWithImagesFiles(JPanel panel) {
        File file = new File(addressField.getText()) ;
        List<File> files = Arrays.asList(file.listFiles()) ;
        for(File currentFile: files){
            if(getExtension(currentFile).equals("jpg")){
                jpgFiles.add(currentFile);
                System.out.println(currentFile.getPath());
                JLabel label = new JLabel(currentFile.getPath()) ;
                label.addMouseListener(new ImageLabelsListener(this));
                panel.add(label) ;
            }
        }
        return panel ;
    }
    public int currentImageIndex = 0;
    public int findCurrentImageIndex(){
        String currentPath = ImageSingleton.getInstance().getCurrentImageAddress() ;
        for(int i = 0 ; i < jpgFiles.size() ; i++){
            if(currentPath.equals(jpgFiles.get(i).getPath())){
                this.currentImageIndex = i ;
                return i ;
            }
        }
        return 0 ;
    }

    public void setNextImage(){
        if(currentImageIndex == jpgFiles.size()-1){
            currentImageIndex = 0 ;
        }else {
            currentImageIndex++;
        }
        System.out.println(jpgFiles.get(currentImageIndex+1).getPath());
        try {
            ImageSingleton.getInstance().setCurrentImageAddress(jpgFiles.get(currentImageIndex).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPreviousImage(){
        if(currentImageIndex == 0){
            currentImageIndex = jpgFiles.size()-1 ;
        }else{
            currentImageIndex-- ;
        }
        try {
            ImageSingleton.getInstance().setCurrentImageAddress(jpgFiles.get(currentImageIndex).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<File> getJpgFiles() {
        return jpgFiles;
    }

    public void setJpgFiles(List<File> jpgFiles) {
        this.jpgFiles = jpgFiles;
    }

    public String getExtension(File file) {
        return file.getPath().substring(file.getPath().lastIndexOf('.')+1) ;
    }
}
