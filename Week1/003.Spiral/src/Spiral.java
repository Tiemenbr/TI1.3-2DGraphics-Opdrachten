import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas);
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics, Canvas canvas) {
        graphics.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        graphics.scale(1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine((int) -canvas.getWidth()/2,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,(int) -canvas.getHeight()/2,0,1000);
        graphics.setColor(Color.BLACK);

        double scale = 4;
        double lastX = 0;
        double lastY = 0;
        double phi = 0;

        for (double r = .01; r < 20; r+=.01) {
            double x = (double) r*phi*Math.cos(phi);
            double y = (double) r*phi*Math.sin(phi);
            graphics.draw(new Line2D.Double(x*scale, y*scale, lastX*scale, lastY*scale));
            phi = r*Math.PI;
            lastX = x;
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
