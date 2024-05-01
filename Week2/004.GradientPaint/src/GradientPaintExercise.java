import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D mouseLocation = new Point2D.Double();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        canvas.setOnMouseDragged(event -> mouseLocation.setLocation(event.getX(),event.getY()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        float[] fractions = new float[]{0f, 0.5f, 1f};
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.YELLOW};
        Point2D center = new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2);
        graphics.setPaint(new RadialGradientPaint(center, 20f, mouseLocation, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE));
        Rectangle2D rectangle = new Rectangle2D.Double(0,0,canvas.getWidth(), canvas.getHeight());
        graphics.fill(rectangle);
        canvas.setOnMouseClicked(mouseEvent -> {
            graphics.setPaint(new RadialGradientPaint(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()), 20f, mouseLocation, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE));
            graphics.fill(rectangle);
        });
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
