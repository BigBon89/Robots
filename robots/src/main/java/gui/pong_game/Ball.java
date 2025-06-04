package gui.pong_game;

import java.util.Random;
import java.awt.*;

public class Ball implements Collidable {
    public int positionX, positionY;
    public int diam;
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
    public Bound getBounds() {
        return new CircleBound(positionX, positionY, diam / 2);
    }

    public Collidable move() {
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
            RectBound platformBound = (RectBound)platform.getBounds();
            Rectangle platformRect = platformBound.getRect();

            CircleBound ballBound = (CircleBound)this.getBounds();

            int platformCenterY = platformRect.y + platformRect.height / 2;
            int offset = ballBound.getCenterY() - platformCenterY;

            double normalizedOffset = offset / (platformRect.height / 2.0);

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

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }
}
