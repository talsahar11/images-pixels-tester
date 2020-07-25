import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class SpectrumOfColors extends JFrame implements MouseListener, Scrollable {
    private ColorEntity[] colors ;
    private float[] colorsSpaceOnTheSpectrum ;
    private int factorColorAppearToPixels ;
    private int remainingPixels ;
    private Map<Integer,Rectangle> rectangles ;
    private HashMap<Integer, JPanel> colorPanels ;
    private List<ColorEntity> selectedColors ;
    private  JButton sendButton ;
    private RequestsService requestsService ;
    public SpectrumOfColors(ColorEntity[] colors) throws Exception {
        this.setSize(1000, 600);
        this.requestsService = new RequestsService() ;
        this.colors = colors ;
        this.selectedColors = new ArrayList<>() ;
        this.colorPanels = new HashMap<>() ;
        JPanel mainPanel = new JPanel() ;
        JPanel southPanel = new JPanel() ;
        sendButton = new JButton("Send Colors") ;
        sendButton.addMouseListener(this);
        southPanel.add(sendButton) ;
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(mainPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS) ;
        mainPanel.setLayout(new GridLayout(20,colors.length/20));
        JPanel currentPanel ;
        Arrays.sort(colors);
        int currentX = 0 ;
        for(ColorEntity color: colors){
                currentPanel = new JPanel();
                currentPanel.setBackground(new Color(color.getRgbVal()));
                currentPanel.setSize(40 * color.getAppears(), 600);
                currentPanel.addMouseListener(this);
                colorPanels.put(color.getRgbVal(), currentPanel);
            for(int i = 0 ; i < color.getAppears() ; i++) {
                mainPanel.add(currentPanel);
                System.out.println("Creating panel");
            }
        }
//        JScrollPane scrPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(southPanel,BorderLayout.SOUTH) ;
        //        add(scrPane);
        rectangles = new HashMap<>() ;
        colorsSpaceOnTheSpectrum = new float[colors.length] ;
        totalQuantityOfColorsAppears = 0 ;
        calculateQuantityOfAppears();
        initFloatArray() ;
        factorColorAppearToPixels = 6000/ totalQuantityOfColorsAppears ;
        remainingPixels = 6000 - factorColorAppearToPixels*totalQuantityOfColorsAppears ;

        this.setVisible(true);
    }



    private int totalQuantityOfColorsAppears ;
    public void calculateQuantityOfAppears() {
        for(ColorEntity color: colors){
            totalQuantityOfColorsAppears += color.getAppears() ;
        }
    }

    public void initFloatArray() {
        for(int i = 0 ; i < colors.length ; i++){
            colorsSpaceOnTheSpectrum[i] = (float)colors[i].getAppears()/totalQuantityOfColorsAppears ;
        }
    }

//    public Rectangle findRectangleClicked(int x, int y){
//
//    }
    public ColorEntity generateColorEntity(int rgb) {
        Color color = new Color(rgb) ;
        ColorEntity entity = new ColorEntity() ;
        entity.setRed(color.getRed());
        entity.setGreen(color.getGreen());
        entity.setBlue(color.getBlue());
        entity.setRgbVal(rgb);
        return entity ;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().getClass() == JPanel.class) {
            JPanel panel = (JPanel) mouseEvent.getSource();
            if (!selectedColors.contains(new ColorEntity(panel.getBackground().getRGB()))) {
                panel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                Color color = panel.getBackground();
                selectedColors.add(generateColorEntity(color.getRGB()));
                for (ColorEntity colorEntity : selectedColors) {
                    System.out.println(colorEntity);
                }
                System.out.println("----------------END---------");
            } else {
                selectedColors.remove(new ColorEntity(panel.getBackground().getRGB()));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        if(mouseEvent.getSource() == this.sendButton) {
            if(selectedColors.size() > 0) {
                try {
                    requestsService.postColors(this.selectedColors.toArray(ColorEntity[]::new));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null,"None of the colors are selected.");
            }
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
        if(mouseEvent.getSource().getClass() == JPanel.class) {
            JPanel panel = (JPanel) mouseEvent.getSource();
            if (!selectedColors.contains(new ColorEntity(panel.getBackground().getRGB()))) {
                Color bgColor = panel.getBackground();
                colorPanels.get(bgColor.getRGB()).setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)) ;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().getClass() == JPanel.class) {
            JPanel panel = (JPanel) mouseEvent.getSource();
            if (!selectedColors.contains(new ColorEntity(panel.getBackground().getRGB()))) {
                panel.setBorder(null);
            }
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle rectangle, int i, int i1) {
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle rectangle, int i, int i1) {
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
