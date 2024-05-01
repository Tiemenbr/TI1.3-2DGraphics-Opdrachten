import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    private ArrayList<Square> squares;
    private Square selectedSquare;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        init();
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void init() {
        this.squares = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.squares.add(new Square());
        }
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        for (int i = this.squares.size()-1; i > 0; i--) {
            graphics.setColor(Color.BLACK);
            graphics.draw(this.squares.get(i).getRectangle2D());
            graphics.setColor(this.squares.get(i).getColor());
            graphics.fill(this.squares.get(i).getRectangle2D());
        }
    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e)
    {
        for (int i = 0; i < this.squares.size(); i++) {
            if (this.squares.get(i).getRectangle2D().contains(e.getX(),e.getY())) {
                this.selectedSquare = this.squares.get(i);
                return;
            }
        }
    }

    private void mouseReleased(MouseEvent e)
    {
        this.selectedSquare = null;
    }

    private void mouseDragged(MouseEvent e)
    {
        if (this.selectedSquare != null) {
            this.selectedSquare.setPosition(e.getX()-50, e.getY()-50);
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

}
