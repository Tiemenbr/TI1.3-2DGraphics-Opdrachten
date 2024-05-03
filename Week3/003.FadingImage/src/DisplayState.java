import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DisplayState implements ImageState {
    private FadingImage fadingImage;
    private ResizableCanvas canvas;
    private BufferedImage[] backgroundImages;
    private int currentImage;
    private double timeSinceStateChange;

    public DisplayState(FadingImage fadingImage, ResizableCanvas canvas, BufferedImage[] backgroundImages, int currentImage) {
        this.canvas = canvas;
        this.backgroundImages = backgroundImages;
        this.currentImage = currentImage;
        this.timeSinceStateChange = 0;
        this.fadingImage = fadingImage;
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        graphics.drawImage(this.backgroundImages[this.currentImage],
                AffineTransform.getScaleInstance(canvas.getWidth()/this.backgroundImages[this.currentImage].getWidth(),
                        canvas.getHeight()/this.backgroundImages[this.currentImage].getHeight()),
                null);
    }

    @Override
    public void update(double deltaTime) {
        this.timeSinceStateChange += deltaTime;
        if (this.timeSinceStateChange > FadingImage.TIME_BETWEEN_IMAGE_CHANGE) {
            this.currentImage++;
            if (this.currentImage > this.backgroundImages.length-1)
                this.currentImage = 0;
            this.fadingImage.changeState(new FadingState(this.fadingImage, this.canvas, this.backgroundImages, this.currentImage));
        }
    }
}
