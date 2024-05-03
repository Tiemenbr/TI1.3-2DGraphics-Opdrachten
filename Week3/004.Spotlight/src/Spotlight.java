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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private BufferedImage background;
    private Shape shape;
    private Form form;
    private enum Form {SQUARE, CIRCLE}

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

        mainPane.setOnMouseMoved(e -> {
            switch (this.form) {
                case SQUARE:
                    this.shape = new Rectangle2D.Double(
                            e.getX()-this.canvas.getWidth()/16,
                            e.getY()-this.canvas.getHeight()/16,
                            this.canvas.getWidth()/8,
                            this.canvas.getHeight()/8);
                    break;
                case CIRCLE:
                    this.shape = new Ellipse2D.Double(
                            e.getX()-this.canvas.getWidth()/16,
                            e.getY()-this.canvas.getHeight()/16,
                            this.canvas.getWidth()/8,
                            this.canvas.getHeight()/8);
                    break;
            }

            mainPane.setOnMouseClicked(event -> {
                if (Form.values().length < this.form.ordinal()+2) {
                    this.form = Form.values()[0];
                    return;
                }
                this.form = Form.values()[this.form.ordinal()+1];
            });
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.transform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setClip(this.shape);
        AffineTransform tx = new AffineTransform();
        tx.scale(this.canvas.getWidth()/this.background.getWidth(),
                this.canvas.getHeight()/this.background.getHeight());
        graphics.drawImage(this.background,tx, null);
        graphics.setClip(null);
    }

    public void init() throws IOException {
        this.background = ImageIO.read(this.getClass().getResource("/images/023.png"));
        this.form = Form.SQUARE;
    }

    public void update(double deltaTime) {

    }

    public static void main(String[] args) {
        launch(Spotlight.class);
    }

}
