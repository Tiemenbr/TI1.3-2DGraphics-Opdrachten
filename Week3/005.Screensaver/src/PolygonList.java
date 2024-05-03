import java.util.ArrayList;
import java.util.Collection;

public class PolygonList extends ArrayList<Polygon> {
    public PolygonList() {
    }

    public PolygonList(Collection<? extends Polygon> c) {
        super(c);
    }

    public PolygonList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public boolean add(Polygon polygon) {
        boolean res = super.add(polygon);
        if (this.size() > Screensaver.MAX_POLYGONS)
            this.remove(0);
        return res;
    }
}
