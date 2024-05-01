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

public class Mirror extends Application {
    ResizableCanvas canvas;
    Double rc;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        rc = 2.5;
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        graphics.scale( 1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine((int) -canvas.getWidth()/2,0,1000,0);
        graphics.setColor(Color.green);
        graphics.drawLine(0,(int) -canvas.getHeight()/2,0,1000);

        graphics.setColor(Color.BLACK);
        graphics.drawLine((int) -canvas.getWidth()/2, (int) (2.5*-canvas.getWidth()/2), (int) canvas.getWidth()/2, (int) (2.5*canvas.getWidth()/2));

        Rectangle2D rectangle = new Rectangle2D.Double(100,-50, 100,100);
        graphics.draw(rectangle);

        // rc is richtingscoëfficiënt van y = 2.5x
        graphics.transform(new AffineTransform(
                (2/(1+(rc*rc)))-1, (2*rc)/(1+(rc*rc)),
                (2*rc)/(1+(rc*rc)), (2*rc*rc)/(1+(rc*rc))-1,
                0, 0
        ));
        graphics.draw(rectangle);
    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
