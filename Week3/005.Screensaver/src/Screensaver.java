import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private PolygonList polygons;
    private ArrayList<Point2D> directionsVertexes;
    private double timeSinceNewPolygon;
    private static final double TIME_BEFORE_NEW_POLYGON = 0.5;
    private static final Color LINE_COLOR = Color.blue;
    public static final double SPEED_VERTEXES = 5;
    public static final int MAX_POLYGONS = 100;
    public static final int AMOUNT_OF_VERTEXES_POLYGON = 4;

    @Override
    public void start(Stage stage) {

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

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setColor(LINE_COLOR);
        this.polygons.draw(graphics);
    }

    public void init() {
        this.polygons = new PolygonList();
        this.directionsVertexes = new ArrayList<>();
        this.polygons.add(createPolygon());
    }

    private Polygon createPolygon() {
        if (this.polygons.isEmpty())  {
            ArrayList<Point2D> vertexes = new ArrayList<>();
            for (int i = 0; i < AMOUNT_OF_VERTEXES_POLYGON; i++) {
                vertexes.add(new Point2D.Double(Math.random()*640.0, Math.random()*480.0));
            }
            for (int i = 0; i < AMOUNT_OF_VERTEXES_POLYGON; i++) {
                double angle = Math.random()*2*Math.PI;
                this.directionsVertexes.add(new Point2D.Double(Math.cos(angle)*SPEED_VERTEXES, Math.sin(angle)*SPEED_VERTEXES));
            }
            return new Polygon(vertexes);
        }
        ArrayList<Point2D> vertexes = new ArrayList<>();
        ArrayList<Point2D> previousVertexes = this.polygons.get(this.polygons.size()-1).getPoints();
        for (int i = 0; i < previousVertexes.size(); i++) {
            Point2D direction = this.directionsVertexes.get(i);
            Point2D vertex = previousVertexes.get(i);
            if ((vertex.getX() + direction.getX() > canvas.getWidth()) || (vertex.getX() + direction.getX() < 0)) {
                if (!(vertex.getX() > this.canvas.getWidth() || vertex.getX() < 0))
                    direction.setLocation(-direction.getX(), direction.getY());
            }
            if ((vertex.getY() + direction.getY() > canvas.getHeight()) || (vertex.getY() + direction.getY() < 0))
                if (!(vertex.getY() > this.canvas.getHeight() || vertex.getY() < 0))
                    direction.setLocation(direction.getX(),-direction.getY());
            vertexes.add(new Point2D.Double(
                    (vertex.getX() > this.canvas.getWidth() || vertex.getX() < 0) ?
                            nearestValue(0,this.canvas.getWidth(),vertex.getX()) :
                            vertex.getX() + direction.getX(),
                    (vertex.getY() > this.canvas.getHeight() || vertex.getY() < 0) ?
                            nearestValue(0,this.canvas.getHeight(),vertex.getY()) :
                            vertex.getY() + direction.getY()));
        }
        return new Polygon(vertexes);
    }

    private double nearestValue(double lowestValue, double highestValue, double pick) {
        return Math.abs(lowestValue - pick) < Math.abs(highestValue - pick) ? lowestValue : highestValue;
    }

    public void update(double deltaTime) {
        this.timeSinceNewPolygon += deltaTime;
        if (this.timeSinceNewPolygon < TIME_BEFORE_NEW_POLYGON)
            return;
        this.polygons.add(createPolygon());
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }
}
