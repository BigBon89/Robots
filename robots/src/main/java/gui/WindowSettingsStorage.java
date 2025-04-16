package gui;

import java.lang.reflect.Field;
import java.util.prefs.Preferences;

public class WindowSettingsStorage {
    private final Preferences prefs;

    public WindowSettingsStorage(Class<?> clazz) {
        this.prefs = Preferences.userNodeForPackage(clazz);
    }

    public void save(String title, WindowSettings settings) {
        for (Field field : WindowSettings.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(WindowProperty.class)) {
                field.setAccessible(true);
                WindowProperty prop = field.getAnnotation(WindowProperty.class);
                String key = title + "_" + prop.key();
                try {
                    Object value = field.get(settings);
                    if (field.getType() == int.class) {
                        prefs.putInt(key, (int) value);
                    } else if (field.getType() == boolean.class) {
                        prefs.putBoolean(key, (boolean) value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public WindowSettings load(String title, WindowSettings defaults) {
        WindowSettings settings = new WindowSettings(
                defaults.x, defaults.y, defaults.width, defaults.height, defaults.minimized
        );

        for (Field field : WindowSettings.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(WindowProperty.class)) {
                field.setAccessible(true);
                WindowProperty prop = field.getAnnotation(WindowProperty.class);
                String key = title + "_" + prop.key();
                try {
                    if (field.getType() == int.class) {
                        int value = prefs.getInt(key, (int) field.get(defaults));
                        field.set(settings, value);
                    } else if (field.getType() == boolean.class) {
                        boolean value = prefs.getBoolean(key, (boolean) field.get(defaults));
                        field.set(settings, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return settings;
    }
}
