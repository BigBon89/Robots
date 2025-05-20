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
    public Rectangle getBounds() {
        return new Rectangle(positionX - width / 2, positionY - height / 2, width, height);
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void move(int additional) {
        CollisionSystem collisionSystem = CollisionSystem.getInstance();
        positionY += additional;
        if (collisionSystem.getCollisionObject(this, GameOverZone.class) != null) {
            positionY -= additional;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(positionX - width / 2, positionY - height / 2, width, height);
    }
}
