import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1024, 768);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        //all coordinates of house outline points
        int[][] coordsHouseOutline = {{300,350}, {300,600}, {700,600}, {700,350}, {500,100}, {300,350}, {300,350}};
        drawLinesFromCoords(graphics, coordsHouseOutline);
        //draw door
        graphics.draw(new Rectangle2D.Double(350,450,75,150));
        //draw window
        graphics.draw(new Rectangle2D.Double(475, 425, 175,100));
    }

    public void drawLinesFromCoords(FXGraphics2D graphics, int[][] coords) {
        for (int i = 1; i < coords.length; i++) {
            graphics.draw(new Line2D.Double(coords[i-1][0],coords[i-1][1],coords[i][0],coords[i][1]));
        }
    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
