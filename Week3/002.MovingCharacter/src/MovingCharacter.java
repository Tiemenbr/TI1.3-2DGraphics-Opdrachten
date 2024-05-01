import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private AnimationTimer animationTimer;
    private Character character;

    @Override
    public void start(Stage stage) throws Exception {
        init();
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        animationTimer = new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        };
        animationTimer.start();

        mainPane.setOnMouseClicked(e -> this.character.jump());

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    public void init() {
        int characterYOffset = 200;
        int speedCharacter = 4;
        this.character = new Character(
                "/images/sprite.png",
                new Point2D.Double(0,characterYOffset),
                speedCharacter);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        this.character.draw(graphics);
    }


    public void update(double deltaTime) {
        this.character.update(this.canvas);
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

}
