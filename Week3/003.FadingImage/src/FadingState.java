import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FadingState implements ImageState {
    private FadingImage fadingImage;
    private ResizableCanvas canvas;
    private BufferedImage[] backgroundImages;
    private int currentImage;
    private int lastImage;
    private double timeSinceStateChange;

    public FadingState(FadingImage fadingImage, ResizableCanvas canvas, BufferedImage[] backgroundImages, int currentImage) {
        this.fadingImage = fadingImage;
        this.canvas = canvas;
        this.backgroundImages = backgroundImages;
        this.currentImage = currentImage;
        this.lastImage = currentImage - 1;
        if (this.lastImage < 0)
            this.lastImage = this.backgroundImages.length - 1;
        this.timeSinceStateChange = 0;
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        graphics.drawImage(this.backgroundImages[this.lastImage],
                AffineTransform.getScaleInstance(canvas.getWidth()/this.backgroundImages[this.lastImage].getWidth(),
                        canvas.getHeight()/this.backgroundImages[this.lastImage].getHeight()),
                null);

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) (this.timeSinceStateChange == 0 ? 0 : this.timeSinceStateChange /FadingImage.TRANSITION_TIME)));
        graphics.drawImage(this.backgroundImages[this.currentImage],
                AffineTransform.getScaleInstance(canvas.getWidth()/this.backgroundImages[this.currentImage].getWidth(),
                        canvas.getHeight()/this.backgroundImages[this.currentImage].getHeight()),
                null);
    }

    @Override
    public void update(double deltaTime) {
        this.timeSinceStateChange += deltaTime;
        if (this.timeSinceStateChange > FadingImage.TRANSITION_TIME)
            this.fadingImage.changeState(new DisplayState(this.fadingImage, this.canvas, this.backgroundImages, this.currentImage));
    }
}
