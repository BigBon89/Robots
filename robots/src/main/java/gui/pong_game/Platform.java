package gui.pong_game;

import java.awt.*;

public class Platform {
    public int positionX, positionY;
    public int width, height;

    public Platform(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public void move(int additional) {
        positionY += additional;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(positionX - width / 2, positionY - height / 2, width, height);
    }
}
