import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Character {
    private BufferedImage[] sprite;
    private Point2D position;
    private double startY;
    private int speed;
    private final int AMOUNTOFWALKFRAMES, AMOUNTOFJUMPFRAMES;
    private boolean isJumping, isLanding;
    private int pixelsPerFrame;

    public Character(String filePath, Point2D startPosition, int startSpeed) {
        try {
            BufferedImage movingCharactar = ImageIO.read(getClass().getResource(filePath));
            this.sprite = new BufferedImage[65];
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    sprite[i*8 + j] = movingCharactar.getSubimage(j * 64, i * 64, 64, 64);
            this.sprite[64] = movingCharactar.getSubimage(0, 8 * 64, 64, 64);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.position = startPosition;
        this.startY = startPosition.getY();
        this.speed = startSpeed;
        this.AMOUNTOFWALKFRAMES = 8;
        this.AMOUNTOFJUMPFRAMES = 4;
        this.pixelsPerFrame = 40;
        this.isJumping = false;
    }

    public void jump() {
        if (!this.isLanding)
            this.isJumping = true;
    }

    public void update(ResizableCanvas canvas) {
        if (this.isJumping) {
            this.position.setLocation(this.position.getX(), this.position.getY() - this.speed);
            if (this.position.getY() < this.startY-(this.AMOUNTOFJUMPFRAMES*this.pixelsPerFrame)) {
                this.isJumping = false;
                this.isLanding = true;
            }
            return;
        }

        else if (this.isLanding) {
            this.position.setLocation(this.position.getX(), this.position.getY() + speed);
            if (this.position.getY() >= this.startY) {
                this.position.setLocation(this.position.getX(), this.startY);
                this.isLanding = false;
            }
            return;
        }

        this.position.setLocation(this.position.getX()+this.speed, this.position.getY());
        if (this.position.getX() > canvas.getWidth())
            this.position.setLocation(-64, this.position.getY());
    }

    public void draw(FXGraphics2D graphics) {
        AffineTransform tx = new AffineTransform();
        tx.translate(this.position.getX(), this.position.getY());

        if (this.isJumping) {
            int frame = ((int) (this.startY - this.position.getY())/pixelsPerFrame) % this.AMOUNTOFWALKFRAMES;
            int firstPositionJumpSprites = 40;
            graphics.drawImage(this.sprite[firstPositionJumpSprites + frame], tx, null);
            return;
        }

        else if (this.isLanding) {
            int frame = ((int) (this.startY - this.position.getY())/pixelsPerFrame) % this.AMOUNTOFWALKFRAMES;
            if (frame == 0) {
                graphics.drawImage(this.sprite[64], tx, null);
                return;
            }

            int lastPositionLandingSprites = 48;
            graphics.drawImage(this.sprite[lastPositionLandingSprites - frame], tx, null);
            return;
        }

        int frame = ((int) this.position.getX()/pixelsPerFrame) % this.AMOUNTOFWALKFRAMES;
        int firstPositionWalkSprites = 32;
        graphics.drawImage(this.sprite[firstPositionWalkSprites+frame], tx, null);
    }
}
