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
    private ImageState imageState;
    public static final double TIMEBETWEENIMAGECHANGE = 3;
    public static final double TRANSITIONTIME = 1;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.imageState = new DisplayState(this,canvas,backgroundImages,0);
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
    }

    public void draw(FXGraphics2D graphics) {
        this.imageState.draw(graphics);
    }


    public void update(double deltaTime) {
        this.imageState.update(deltaTime);
    }

    public void changeState(ImageState state) {
        this.imageState = state;
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
