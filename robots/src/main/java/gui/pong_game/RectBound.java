package gui.pong_game;

import java.awt.Rectangle;

import static java.lang.Math.clamp;

public final class RectBound implements Bound {
    private final Rectangle rect;

    public RectBound(Rectangle rect) {
        this.rect = rect;
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    public boolean intersects(Bound other) {
        if (other instanceof RectBound r) {
            return rect.intersects(r.rect);
        } else if (other instanceof CircleBound c) {
            int closestX = clamp(c.getCenterX(), rect.x, rect.x + rect.width);
            int closestY = clamp(c.getCenterY(), rect.y, rect.y + rect.height);

            int dx = c.getCenterX() - closestX;
            int dy = c.getCenterY() - closestY;

            return dx * dx + dy * dy <= c.getRadius() * c.getRadius();
        }
        return false;
    }
}
