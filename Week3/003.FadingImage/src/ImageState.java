import org.jfree.fx.FXGraphics2D;

public interface ImageState {
    void draw(FXGraphics2D graphics);
    void update(double deltaTime);
}
