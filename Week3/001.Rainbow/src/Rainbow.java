import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth()/2,canvas.getHeight()/2);

        Font font = new Font("Arial", Font.PLAIN, 120);
        GlyphVector glyphVector = font.createGlyphVector(graphics.getFontRenderContext(), "regenboog");
        for (int i = 0; i < glyphVector.getNumGlyphs(); i++) {
            glyphVector.setGlyphTransform(i,AffineTransform.getRotateInstance(i*Math.PI/glyphVector.getNumGlyphs()-Math.PI/2 ));
        }
        graphics.translate(-glyphVector.getOutline().getBounds2D().getWidth()/2, glyphVector.getOutline().getBounds2D().getHeight()/2);
        for (int i = 0; i < glyphVector.getNumGlyphs(); i++) {
            graphics.setColor(Color.BLACK);
            graphics.draw(glyphVector.getGlyphOutline(i));
            graphics.setColor(Color.getHSBColor((float) ((double) i/glyphVector.getNumGlyphs()),1f,1f));
            graphics.fill(glyphVector.getGlyphOutline(i));
        }
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}