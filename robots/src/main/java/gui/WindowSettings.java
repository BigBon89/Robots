package gui;

public class WindowSettings {
    @WindowProperty(key = "x")
    public int x;

    @WindowProperty(key = "y")
    public int y;

    @WindowProperty(key = "width")
    public int width;

    @WindowProperty(key = "height")
    public int height;

    @WindowProperty(key = "minimized")
    public boolean minimized;

    public WindowSettings(int x, int y, int width, int height, boolean minimized) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minimized = minimized;
    }
}
