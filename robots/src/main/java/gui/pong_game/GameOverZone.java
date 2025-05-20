package gui.pong_game;

import java.awt.*;

public class GameOverZone implements Collidable {
    public int positionX, positionY;
    public int width, height;

    public GameOverZone() {

    }

    public void resize(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, width, height);
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 50));
        g.fillRect(positionX, positionY, width, height);
    }
}
