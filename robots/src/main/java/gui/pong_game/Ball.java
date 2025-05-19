package gui.pong_game;

import java.awt.*;

public class Ball implements Collidable {
    public int positionX, positionY;
    public int diam;
    public int velocityX = 2;
    public int velocityY = 1;

    public Ball(int positionX, int positionY, int diam) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.diam = diam;
    }

    @Override
    public Rect getBounds() {
        return new Rect(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }

    void move() {
        CollisionSystem collisionSystem = CollisionSystem.getInstance();

        int newX = positionX + velocityX;
        int newY = positionY + velocityY;
        positionX = newX;
        positionY = newY;

        Collidable obj = collisionSystem.getCollisionObject(this);
        if (obj == null) {
            return;
        }
        if (obj instanceof Platform) {
            velocityX *= -1;
        } else {
            velocityY *= -1;
        }
    }

    void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }
}
