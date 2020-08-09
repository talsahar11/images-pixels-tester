public class PixelsNode {
    private PixelsNode up ;
    private PixelsNode left ;
    private PixelsNode down ;
    private PixelsNode right ;
    private int x,y ;
    public PixelsNode(int x, int y) {
        this.x = x;
        this.y = y ;
    }

    public PixelsNode getUp() {
        return up;
    }

    public void setUp(PixelsNode up) {
        this.up = up;
    }

    public PixelsNode getLeft() {
        return left;
    }

    public void setLeft(PixelsNode left) {
        this.left = left;
    }

    public PixelsNode getDown() {
        return down;
    }

    public void setDown(PixelsNode down) {
        this.down = down;
    }

    public PixelsNode getRight() {
        return right;
    }

    public void setRight(PixelsNode right) {
        this.right = right;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
