package gui.pong_game;

import java.awt.*;

public class Ball implements Collidable {
    private int positionX, positionY;
    private int diam;
    private int velocityX = 2;
    private int velocityY = 1;

    public Ball(int positionX, int positionY, int diam) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.diam = diam;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
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
        if (collisionObject instanceof Platform) {
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
