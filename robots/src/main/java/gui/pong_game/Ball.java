package gui.pong_game;

import java.awt.*;

public class Ball implements Collidable {
    private int positionX, positionY;
    private final int diam;
    private int velocityX = 2;
    private int velocityY = 0;

    public Ball(int positionX, int positionY, int diam) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.diam = diam;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setVelocity(int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
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

            velocityY = (int)(normalizedOffset * 5);

            if (velocityY == 0) {
                velocityY = 1;
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
