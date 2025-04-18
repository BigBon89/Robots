package gui;

import log.Logger;

import java.lang.reflect.Field;
import java.util.prefs.Preferences;

public class WindowSettingsStorage {
    private final Preferences prefs;

    public WindowSettingsStorage(Class<?> clazz) {
        this.prefs = Preferences.userNodeForPackage(clazz);
    }

    public void save(String title, WindowSettings settings) {
        for (Field field : WindowSettings.class.getFields()) {
            try {
                String key = title + "_" + field.getName();
                Class<?> type = field.getType();
                Object value = field.get(settings);

                if (type == int.class) {
                    prefs.putInt(key, (Integer) value);
                } else if (type == boolean.class) {
                    prefs.putBoolean(key, (Boolean) value);
                }
            } catch (Exception e) {
                Logger.debug("Ошибка:" + e.getMessage());
            }
        }
    }

    public WindowSettings load(String title, WindowSettings defaults) {
        WindowSettings result = new WindowSettings(
                defaults.x, defaults.y, defaults.width, defaults.height, defaults.minimized);

        for (Field field : WindowSettings.class.getFields()) {
            try {
                String key = title + "_" + field.getName();
                Class<?> type = field.getType();

                if (type == int.class) {
                    int value = prefs.getInt(key, field.getInt(defaults));
                    field.setInt(result, value);
                } else if (type == boolean.class) {
                    boolean value = prefs.getBoolean(key, field.getBoolean(defaults));
                    field.setBoolean(result, value);
                }
            } catch (Exception e) {
                Logger.debug("Ошибка:" + e.getMessage());
            }
        }

        return result;
    }
}
