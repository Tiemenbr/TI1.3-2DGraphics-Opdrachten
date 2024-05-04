import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PolygonList extends ArrayList<Polygon> implements Drawable {
    public PolygonList() {
    }

    public PolygonList(Collection<? extends Polygon> c) {
        super(c);
    }

    public PolygonList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        Iterator<Polygon> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw(graphics);
        }
    }

    @Override
    public boolean add(Polygon polygon) {
        boolean res = super.add(polygon);
        if (this.size() > Screensaver.MAX_POLYGONS)
            this.remove(0);
        return res;
    }
}
