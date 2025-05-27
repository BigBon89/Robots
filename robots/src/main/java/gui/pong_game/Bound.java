package gui.pong_game;

public sealed interface Bound permits RectBound, CircleBound {
    boolean intersects(Bound other);
}
