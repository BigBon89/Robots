package gui.pong_game;

import java.awt.*;

public class Platform implements Collidable {
    public int positionX, positionY;
    public int width, height;

    public Platform(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rect getBounds() {
        return new Rect(positionX - width / 2, positionY - height / 2, width, height);
    }

    public void move(int additional) {
        CollisionSystem collisionSystem = CollisionSystem.getInstance();
        positionY += additional;
        if (collisionSystem.getCollisionObject(this) != null) {
            positionY -= additional;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(positionX - width / 2, positionY - height / 2, width, height);
    }
}
