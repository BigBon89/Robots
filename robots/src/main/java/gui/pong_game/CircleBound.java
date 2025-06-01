package gui.pong_game;

import java.awt.Rectangle;

import static java.lang.Math.clamp;

public final class CircleBound implements Bound {
    private final int centerX, centerY, radius;

    public CircleBound(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public boolean intersects(Bound other) {
        if (other instanceof CircleBound c) {
            int dx = centerX - c.centerX;
            int dy = centerY - c.centerY;
            int distSq = dx * dx + dy * dy;
            int radiusSum = radius + c.radius;
            return distSq <= radiusSum * radiusSum;
        } else if (other instanceof RectBound r) {
            Rectangle rect = r.getRect();
            int closestX = clamp(centerX, rect.x, rect.x + rect.width);
            int closestY = clamp(centerY, rect.y, rect.y + rect.height);

            int dx = centerX - closestX;
            int dy = centerY - closestY;

            return dx * dx + dy * dy <= radius * radius;
        }
        return false;
    }
}
