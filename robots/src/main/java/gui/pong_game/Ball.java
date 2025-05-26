package gui.pong_game;

import java.util.Random;
import java.awt.*;

public class Ball implements Collidable {
    private int positionX, positionY;
    private final int diam;
    private int velocityX;
    private int velocityY;
    private int defaultSpeed;
    private final Random random;

    public Ball(int positionX, int positionY, int diam, int defaultSpeed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.diam = diam;
        this.defaultSpeed = defaultSpeed;
        velocityX = defaultSpeed;
        velocityY = 0;
        random = new Random();
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void resetVelocity() {
        velocityX = defaultSpeed;
        velocityY = 0;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }

    Collidable move() {
        CollisionSystem collisionSystem = CollisionSystem.getInstance();

        positionX += velocityX;
        positionY += velocityY;

        Collidable collisionObject = collisionSystem.getCollisionObject(this);
        if (collisionObject == null) {
            return null;
        }
        positionX -= velocityX;
        positionY -= velocityY;
        if (collisionObject instanceof Platform platform) {
            int platformCenterY = platform.getBounds().y + platform.getBounds().height / 2;
            int ballCenterY = this.getBounds().y + this.getBounds().height / 2;
            int offset = ballCenterY - platformCenterY;

            double normalizedOffset = offset / (platform.getBounds().height / 2.0);

            velocityY = (int)(normalizedOffset * 5 * (1.0f + random.nextFloat()));

            if (velocityY == 0) {
                velocityY = 2;
            }

            velocityX *= -1;
        } else {
            velocityY *= -1;
        }
        return collisionObject;
    }

    void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }
}
