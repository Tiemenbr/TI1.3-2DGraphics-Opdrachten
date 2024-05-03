import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public class Polygon implements Drawable {
    private final ArrayList<Point2D> points;

    public Polygon(Collection<Point2D> vertexes) {
        this.points = new ArrayList<>();
        this.points.addAll(vertexes);
    }

    public ArrayList<Point2D> getPoints() {
        return this.points;
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (int i = 0; i < this.points.size(); i++) {
            int nextPoint = i+1;
            if (nextPoint == this.points.size())
                nextPoint = 0;
            graphics.drawLine((int) points.get(i).getX(),
                    (int) points.get(i).getY(),
                    (int) points.get(nextPoint).getX(),
                    (int) points.get(nextPoint).getY());
        }
    }
}
