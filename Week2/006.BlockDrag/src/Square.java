import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Square {
    private Color color;
    private Rectangle2D rectangle2D;

    public Square() {
        this.color = new Color((int) (Math.random()*256),(int) (Math.random()*256),(int) (Math.random()*256));
        this.rectangle2D = new Rectangle2D.Double(0,0,100,100);
    }

    public void setPosition(double x, double y) {
        this.rectangle2D = new Rectangle2D.Double(x,y,100,100);
    }

    public Color getColor() {
        return color;
    }

    public Rectangle2D getRectangle2D() {
        return rectangle2D;
    }
}
