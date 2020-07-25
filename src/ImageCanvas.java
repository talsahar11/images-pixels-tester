import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ImageCanvas extends JFrame implements KeyListener, MouseMotionListener, ActionListener, MouseListener {
    private BufferedImage chosenImage ;
    private ImageChooser imageChooser ;
    private JPanel colorCircle ;
    private JLabel image ;
    private JTextField rText ;
    private JTextField gText ;
    private JTextField bText ;
    private RequestsService requestsService ;
    private Color currentCircleColor ;
    private Rectangle lastRectangle ;
    private int currentX ;
    private int currentY ;
    private Color currentRgb ;
    private int mouseStartLocationX, mouseStartLocationY ;
    private Analyzer analyzer ;
    public ImageCanvas(ImageChooser imageChooser) throws IOException {
        this.requestsService = new RequestsService();
        this.imageChooser = imageChooser ;
        this.chosenImage = ImageSingleton.getInstance().getCurrentImage() ;
        chosenImage = resize(chosenImage, 600) ;
        image = new JLabel(new ImageIcon(chosenImage)) ;
        this.setSize(chosenImage.getWidth()+200, chosenImage.getHeight()+39);
       initColorCircle() ;

        //----Right-Panel------------

        JPanel rightPanel = new JPanel() ;

        JPanel rPanel = new JPanel() ;
        JPanel gPanel = new JPanel() ;
        JPanel bPanel = new JPanel() ;
        JButton backButton = new JButton("Back") ;

        rText = new JTextField("--Red--") ;
        gText = new JTextField("-Green-") ;
        bText = new JTextField("-Blue--") ;

        JLabel rLabel = new JLabel("Red:") ;
        JLabel gLabel = new JLabel("Green:") ;
        JLabel bLabel = new JLabel("Blue:") ;

        rText.setFocusable(false);
        gText.setFocusable(false);
        bText.setFocusable(false);

        rPanel.add(rLabel) ;
        rPanel.add(rText);
        gPanel.add(gLabel) ;
        gPanel.add(gText);
        bPanel.add(bLabel) ;
        bPanel.add(bText);

        rPanel.setLayout(new FlowLayout());
        gPanel.setLayout(new FlowLayout());
        bPanel.setLayout(new FlowLayout());

        rightPanel.setLayout(new GridLayout(5,1));
        rightPanel.add(rPanel) ;
        rightPanel.add(gPanel) ;
        rightPanel.add(bPanel) ;
        rightPanel.add(colorCircle) ;
        rightPanel.add(backButton) ;

        //------Listeners----------

        backButton.addActionListener(this);
        image.addMouseMotionListener(this);
        image.addMouseListener(this);
        //-----Adding To The main Frame -----------
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(image, BorderLayout.WEST) ;
        this.getContentPane().add(rightPanel,BorderLayout.EAST);
        this.setFocusable(true);
        this.setVisible(true);
    }

    private BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }
//    @Override
//    public void paint(Graphics g) {

//    }

    public void changeRgbTexts(int r, int g, int b){
        this.rText.setText(String.valueOf(r));
        this.gText.setText(String.valueOf(g));
        this.bText.setText(String.valueOf(b));
    }

    public void initColorCircle() {
        currentCircleColor = new Color(Color.white.getRGB()) ;
        this.colorCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);

                Graphics2D g2d = (Graphics2D) grphcs;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(currentCircleColor);
                //g2d.drawOval(10,10,100,100);//I like fill :P
                g2d.fillOval(0,10,80,80);

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(80, 80);
            }
        };
    }

    //--When the back buttons clicked-------
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        imageChooser.setVisible(true);
        this.dispose();
    }

    //-----------------Mouse events-------------------

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        currentX = mouseEvent.getX();
        currentY = mouseEvent.getY();
        currentRgb = new Color(chosenImage.getRGB(currentX,currentY)) ;
        ColorEntity color = new ColorEntity() ;
        color.setRed(currentRgb.getRed());
        color.setBlue(currentRgb.getBlue());
        color.setGreen(currentRgb.getGreen());
        color.setRgbVal(currentRgb.getRGB());
        Graphics g = image.getGraphics() ;
        Graphics2D g2d = (Graphics2D) g ;
        g2d.setColor(Color.red);
        g.fillOval(currentX,currentY,5,5);
        int chosenOption = JOptionPane.showConfirmDialog(null, "Do you want to save this color? ",null,JOptionPane.YES_NO_OPTION);
        image.repaint();
        if(chosenOption == JOptionPane.YES_OPTION) {
            try {
                this.requestsService.postColor(color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseStartLocationX = mouseEvent.getX() ;
        mouseStartLocationY = mouseEvent.getY() ;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        lastRectangle = new Rectangle() ;
        Graphics2D g2d = (Graphics2D) image.getGraphics() ;
        g2d.drawImage(chosenImage,0,0,null) ;
        if(mouseEvent.getX() > mouseStartLocationX) {
            lastRectangle.x = mouseStartLocationX ;
            lastRectangle.width = mouseEvent.getX()-mouseStartLocationX ;
            if (mouseEvent.getY() > mouseStartLocationY) {
                lastRectangle.height = mouseEvent.getY() - mouseStartLocationY ;
                lastRectangle.y = mouseStartLocationY ;
            }else{
                lastRectangle.height = mouseStartLocationY-mouseEvent.getY() ;
                lastRectangle.y = mouseEvent.getY() ;
            }
        }else{
            lastRectangle.x = mouseEvent.getX() ;
            lastRectangle.width = mouseStartLocationX - mouseEvent.getX() ;
            if (mouseEvent.getY() > mouseStartLocationY) {
                lastRectangle.y = mouseStartLocationY ;
                lastRectangle.height = mouseEvent.getY() - mouseStartLocationY ;
            }else{
                lastRectangle.y = mouseEvent.getY() ;
                lastRectangle.height = mouseStartLocationY - mouseEvent.getY() ;
            }
        }
        g2d.drawRect(lastRectangle.x,lastRectangle.y, lastRectangle.width, lastRectangle.height);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(lastRectangle != null) {
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            g2d.drawRect(lastRectangle.x, lastRectangle.y, lastRectangle.width, lastRectangle.height);
            int chosenOption = JOptionPane.showConfirmDialog(null,"Do you want to analyze the chosen rectangle?", null, JOptionPane.YES_NO_OPTION) ;
            if(chosenOption == JOptionPane.YES_OPTION){
                this.analyzer = new Analyzer(chosenImage, lastRectangle) ;
                try {
                    analyzer.scanRect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                lastRectangle = null ;
                image.repaint();

            }
        }
    }



    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        currentX = mouseEvent.getX();
        currentY = mouseEvent.getY();
        currentRgb = new Color(chosenImage.getRGB(currentX,currentY)) ;
        changeRgbTexts(currentRgb.getRed(), currentRgb.getGreen(), currentRgb.getBlue());
        this.currentCircleColor = currentRgb ;
        this.colorCircle.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode() ;
        System.out.println("Im Working After ALL");
        switch (keyCode) {
            case KeyEvent.VK_LEFT : imageChooser.setNextImage() ;
                replaceImage();
                break ;
            case KeyEvent.VK_RIGHT: imageChooser.setPreviousImage();
                replaceImage();
            default: ;
        }
    }
    public void replaceImage(){
        chosenImage = ImageSingleton.getInstance().getCurrentImage() ;
        chosenImage = resize(chosenImage, 600) ;
        this.setSize(chosenImage.getWidth()+200, chosenImage.getHeight()+39);

        image.setIcon(new ImageIcon(chosenImage));
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
