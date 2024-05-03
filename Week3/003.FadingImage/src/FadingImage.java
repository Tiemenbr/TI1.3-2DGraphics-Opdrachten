import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] backgroundImages;
    private int currentImage;
    private double timeSinceImageChange;
    private final double TIMEBETWEENIMAGECHANGE = 3;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }

    public void init() throws IOException {
        File[] images;
        try {
            File folder = new File(this.getClass().getResource("/images/").getPath());
            images = folder.listFiles();
            System.out.printf("Amount of images in dir: %s%n", images.length);
        } catch (NullPointerException e) {
            System.out.println("images don't exits");
            throw new NullPointerException();
        }
        this.backgroundImages = new BufferedImage[images.length];
        for (int i = 0; i < images.length; i++) {
            backgroundImages[i] = ImageIO.read(images[i]);
        }
        this.timeSinceImageChange = 0;
        this.currentImage = 0;
    }

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


    public void update(double deltaTime) {
        this.timeSinceImageChange += deltaTime;
        if (this.timeSinceImageChange > this.TIMEBETWEENIMAGECHANGE) {
            this.currentImage++;
            if (this.currentImage > this.backgroundImages.length-1)
                this.currentImage = 0;
            this.timeSinceImageChange = 0;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
