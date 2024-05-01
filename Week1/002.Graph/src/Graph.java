import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Graph extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()), canvas);
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics, Canvas canvas) {

        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        graphics.scale( 1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine((int) -canvas.getWidth()/2,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,(int) -canvas.getHeight()/2,0,1000);
        graphics.setColor(Color.black);

        double resolution = 0.1;
        double scale = 250.0;
        double lastY = Math.pow(-10, 3);

        for(double x = -2; x < 2; x += resolution)
        {
            float y = (float) Math.pow(x,3);
            graphics.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
            lastY = y;
        }
    }




    public static void main(String[] args) {
        launch(Graph.class);
    }

}
