import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scanner {
    private boolean[][] isStepped;
    private BufferedImage imageToScan;
    private Color[][] imageColors;
    private int imageHeight, imageWidth, currentX, currentY;
    private MatrixCell[][] screenMatrix;
    private List<Point> targetedCells;

    public Scanner(BufferedImage image, int x, int y) {
        initVariables(image, x, y) ;


        initScreenMatrix();
        fillMatrixCells();
        scanMatrix();
    }
    public void initVariables(BufferedImage image, int x, int y){
        imageToScan = image ;
        this.currentX = x;
        this.currentY = y;
        this.imageWidth = imageToScan.getWidth();
        this.imageHeight = imageToScan.getHeight();
        this.isStepped = new boolean[imageWidth][imageHeight];

        //Maps all of the image pixels into a two dimensional array
        this.imageColors = new Color[imageWidth][imageHeight];
        for (int i = 0; i < imageToScan.getWidth(); i++) {
            for (int j = 0; j < imageToScan.getHeight(); j++) {
                imageColors[i][j] = new Color(imageToScan.getRGB(i, j));
            }
        }

        targetedCells = new ArrayList<>();
    }

    //--Sets the matrix props and dimensions by the image provided----
    public void initScreenMatrix() {
        this.screenMatrix = new MatrixCell[10][10];
        MatrixCell currentCell ;
        int cellWidth = findMatrixCellWidth();
        int cellHeight = findMatrixCellHeight();
        Dimension cellSize = new Dimension(cellWidth,cellHeight) ;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                currentCell = new MatrixCell(cellSize, j* cellWidth, i* cellHeight) ;
                screenMatrix[i][j] = currentCell ;
            }
        }
    }

    //---Fills the matrix cells with the coordinates of every human colored cell in their area-----
    public void fillMatrixCells() {
        float hue;
        for(MatrixCell[] cellOfCells: screenMatrix){
            for(MatrixCell cell: cellOfCells) {
                for (int i = 0; i < cell.getSize().width; i++) {
                    for (int j = 0; j < cell.getSize().height; j++) {
                        hue = getHSB(new Color(imageToScan.getRGB(cell.getLocationX() + i, cell.getLocationY() + j)))[0];
                        hue *= 360;
                        if (hue > 260 || hue < 35) {
                            cell.add(new Point(cell.getLocationX() + i, cell.getLocationY() + j));
                        }
                    }
                }
                System.out.println(cell.getPointsToScan().size());
            };
        }
    }

    //---Scanning every matrix cell---
    //---The scan start coordinates for each cell will be a random points from the points collected by the fillMatrixCells() function
    public void scanMatrix(){
        for(int i = 25 ; i > 0 ; i--) {
            int listSize;
            int loopCounter = 0;
            for (MatrixCell[] cellOfCells : screenMatrix) {
                for (MatrixCell cell : cellOfCells) {
                    int x = rand.nextInt(10), y = rand.nextInt(10);
                    listSize = screenMatrix[x][y].getPointsToScan().size();
                    if (listSize > 0) {
                        if (loopCounter % 10 == 0) {
                            Point pointToScan = screenMatrix[x][y].getPointsToScan().get(rand.nextInt(listSize));
                            currentColor = new Color(imageToScan.getRGB(pointToScan.x, pointToScan.y));
                            counter = 0;
                            scan(pointToScan.x, pointToScan.y, isStepped);
                            currentRGBToPaint = currentRGBToPaint + 2000;
                            System.out.println("Scanning again");
                        }
                    }
                    loopCounter++;
                }
            }
        }
    }

    //--Calculates the size of each cell of the matrix while ignore from the left overs-----
    public int findMatrixCellWidth(){
        int leftOvers = imageWidth % 10;
        if(leftOvers != 0) {
            imageWidth -= leftOvers ;
        }
        return imageWidth/10 ;
    }
    public int findMatrixCellHeight(){
        int leftOvers = imageHeight % 10;
        if(leftOvers != 0) {
            imageHeight -= leftOvers ;
        }
        return imageHeight/10 ;
    }

    //--The scan function will start scanning from a given coordinates--
    //--It operates by recursively call itself for each direction(UP, DOWN, LEFT, RIGHT)--
    //--On each cell the function checks its color and before recall to any direction, the color will be copmared to the color of the cell in the next direction--

    Random rand = new Random();
    int counter = 0;
    int currentRGBToPaint = Color.black.getRGB() ;
    Color currentColor;
    Color nextColor;
    public void scan(int currentX, int currentY, boolean[][] isStepped) {
        counter++;
        //--The safe system limit for the recursively function calls is 2000--
        if (counter <= 2000) {
            imageToScan.setRGB(currentX, currentY, currentRGBToPaint);
            targetedCells.add(new Point(currentX, currentY));
            isStepped[currentX][currentY] = true;
            System.out.println(targetedCells.size());

            //--Go down--
            if (currentY > 0) {
                if (!isStepped[currentX][currentY - 1]) {
                    nextColor = new Color(imageToScan.getRGB(currentX, currentY - 1));
                    if (isColorsMatches(currentColor, nextColor)) {
                        scan(currentX, currentY - 1, isStepped);
                    }
                }
            }

            //--Go Right--
            if (currentX + 1 < imageWidth) {
                if (!isStepped[currentX + 1][currentY]) {
                    nextColor = new Color(imageToScan.getRGB(currentX + 1, currentY));
                    if (isColorsMatches(currentColor, nextColor)) {
                        scan(currentX + 1, currentY, isStepped);
                    }
                }
            }

            //--Go Up--
            if (currentY + 1 < imageHeight) {
                if (!isStepped[currentX][currentY + 1]) {
                    nextColor = new Color(imageToScan.getRGB(currentX, currentY + 1));
                    if (isColorsMatches(currentColor, nextColor)) {
                        scan(currentX, currentY + 1, isStepped);
                    }
                }
            }

            //--Go Left--
            if (currentX > 0) {
                if (!isStepped[currentX - 1][currentY]) {
                    nextColor = new Color(imageToScan.getRGB(currentX - 1, currentY));
                    if (isColorsMatches(currentColor, nextColor)) {
                        scan(currentX - 1, currentY, isStepped);
                    }
                }
            }
        }
        return ;
    }

    //--Defines the way of the scan function to know when two color are similar--
    public boolean isColorsMatches(Color color1, Color color2) {
        float[] hsb1 = getHSB(color1), hsb2 = getHSB(color2);
        float hue1 = hsb1[0] * 360, hue2 = hsb2[0] * 360;
        if (hue1 - hue2 >= 0) {
            if (hue1 - hue2 < 15) {
                return true;
            } else {
                return false;
            }
        } else {
            if (hue2 - hue1 < 15) {
                return true;
            } else {
                return false;
            }
        }
    }

    public float[] getHSB(Color color) {
        float[] hsbVals = new float[3];
        return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbVals);
    }
}
