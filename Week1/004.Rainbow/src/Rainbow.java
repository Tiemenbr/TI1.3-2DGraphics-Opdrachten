import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas);
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics, Canvas canvas) {
        graphics.translate(canvas.getWidth()/2, canvas.getHeight());
        graphics.scale(1,-1);
        double quarterWidth = canvas.getWidth()/4;
        double quarterHeight = canvas.getHeight()/4;
        double z = Math.PI/500;
        double radiusBinnen = 0.75 * quarterWidth;
        double radiusBuiten = quarterWidth;
        for(double i = 0; i < 500; i+=0.25) {
            double x1 = radiusBinnen * Math.cos(z*i);
            double y1 = quarterHeight + radiusBinnen * Math.sin(z*i);
            double x2 = radiusBuiten * Math.cos(z*i);
            double y2 = quarterHeight + radiusBuiten * Math.sin(z*i);
            graphics.setColor(Color.getHSBColor((float) i/500.0f, 1, 1));
            graphics.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
        }
    }
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }
}
