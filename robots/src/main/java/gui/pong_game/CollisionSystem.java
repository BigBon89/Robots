package gui.pong_game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionSystem {
    private static final CollisionSystem instance = new CollisionSystem();
    private final List<Collidable> collidables = new ArrayList<>();

    public int screenHeight;

    private CollisionSystem() {

    }

    public static CollisionSystem getInstance() {
        return instance;
    }

    public void add(Collidable c) {
        collidables.add(c);
    }

    public Collidable getCollisionObject(Collidable source, Class<?>... ignoreClasses) {
        Bound bounds = source.getBounds();

        if (bounds instanceof RectBound rect) {
            Rectangle r = rect.getRect();
            if (r.y < 0 || r.y + r.height > screenHeight) {
                return () -> new RectBound(new Rectangle(0, 0, 0, 0));
            }
        } else if (bounds instanceof CircleBound circle) {
            int top = circle.getCenterY() - circle.getRadius();
            int bottom = circle.getCenterY() + circle.getRadius();
            if (top < 0 || bottom > screenHeight) {
                return () -> new RectBound(new Rectangle(0, 0, 0, 0));
            }
        }

        outer:
        for (Collidable other : collidables) {
            if (other == source) {
                continue;
            }

            for (Class<?> cls : ignoreClasses) {
                if (cls.isInstance(other)) {
                    continue outer;
                }
            }

            if (bounds.intersects(other.getBounds())) {
                return other;
            }
        }

        return null;
    }
}
