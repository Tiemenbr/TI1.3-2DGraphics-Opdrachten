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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(100,100);
        GeneralPath blackShape = new GeneralPath();
        blackShape.moveTo(150,0);
        blackShape.curveTo(250,0,250,150,150,150);
        blackShape.curveTo(50,150,50,300,150,300);
        blackShape.curveTo(350,300,350,0,150,0);
        blackShape.closePath();
        graphics.draw(blackShape);
        GeneralPath whiteShape = new GeneralPath();
        whiteShape.moveTo(150,0);
        whiteShape.curveTo(250,0,250,150,150,150);
        whiteShape.curveTo(50,150,50,300,150,300);
        whiteShape.curveTo(-50,300,-50,0,150,0);
        graphics.draw(whiteShape);
        Area a = new Area(blackShape);
        a.subtract(new Area(whiteShape));
        a.add(new Area(new Ellipse2D.Double(125,50,50,50)));
        a.subtract(new Area(new Ellipse2D.Double(125,200,50,50)));
        graphics.fill(a);
    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
