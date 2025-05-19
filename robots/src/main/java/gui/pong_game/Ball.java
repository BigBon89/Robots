package gui.pong_game;

import java.awt.*;

public class Ball {
    public int positionX, positionY;
    public int diam;

    public Ball(int positionX, int positionY, int diam) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.diam = diam;
    }

    void move(CollisionSystem collisionSystem, Platform pl1, Platform pl2) {

    }

    void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(positionX - diam / 2, positionY - diam / 2, diam, diam);
    }
}
