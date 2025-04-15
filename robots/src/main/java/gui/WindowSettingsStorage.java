package gui;

import java.util.prefs.Preferences;

public class WindowSettingsStorage {
    private final Preferences prefs;

    public WindowSettingsStorage(Class<?> clazz) {
        this.prefs = Preferences.userNodeForPackage(clazz);
    }

    public void save(String title, WindowSettings settings) {
        prefs.putInt(title + "_x", settings.x);
        prefs.putInt(title + "_y", settings.y);
        prefs.putInt(title + "_width", settings.width);
        prefs.putInt(title + "_height", settings.height);
        prefs.putBoolean(title + "_minimized", settings.minimized);
    }

    public WindowSettings load(String title, WindowSettings defaults) {
        int x = prefs.getInt(title + "_x", defaults.x);
        int y = prefs.getInt(title + "_y", defaults.y);
        int width = prefs.getInt(title + "_width", defaults.width);
        int height = prefs.getInt(title + "_height", defaults.height);
        boolean minimized = prefs.getBoolean(title + "_minimized", defaults.minimized);
        return new WindowSettings(x, y, width, height, minimized);
    }
}
