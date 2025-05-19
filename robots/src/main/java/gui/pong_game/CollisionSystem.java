package gui.pong_game;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem {
    private static final CollisionSystem instance = new CollisionSystem();
    private final List<Collidable> collidables = new ArrayList<>();

    public int screenWidth;
    public int screenHeight;

    private CollisionSystem() {

    }

    public static CollisionSystem getInstance() {
        return instance;
    }

    public void add(Collidable c) {
        collidables.add(c);
    }

    public Collidable getCollisionObject(Collidable source) {
        Rect bounds = source.getBounds();

        if (bounds.x < 0 || bounds.y < 0 ||
                bounds.x + bounds.width > screenWidth ||
                bounds.y + bounds.height > screenHeight) {
            return () -> new Rect(0, 0, 0, 0);
        }

        for (Collidable other : collidables) {
            if (other == source) {
                continue;
            }
            if (bounds.intersects(other.getBounds())) {
                return other;
            }
        }

        return null;
    }
}
