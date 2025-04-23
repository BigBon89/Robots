package gui;

public class WindowSettings {
    public int x, y, width, height;
    public boolean minimized;

    public WindowSettings(int x, int y, int width, int height, boolean minimized) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minimized = minimized;
    }

    @Override
    public WindowSettings clone() {
        try {
            return (WindowSettings) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
