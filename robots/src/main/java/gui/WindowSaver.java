package gui;

import javax.swing.*;

public class WindowSaver {
    private final JDesktopPane desktopPane;
    private final WindowSettingsStorage storage;

    public WindowSaver(JDesktopPane pane) {
        this.desktopPane = pane;
        this.storage = new WindowSettingsStorage(MainApplicationFrame.class);
    }

    public void saveWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String title = frame.getTitle();
            WindowSettings settings = new WindowSettings(
                    frame.getX(), frame.getY(),
                    frame.getWidth(), frame.getHeight(),
                    frame.isIcon()
            );
            storage.save(title, settings);
        }
    }

    public void restoreWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String title = frame.getTitle();
            WindowSettings defaults = new WindowSettings(
                    frame.getX(), frame.getY(),
                    frame.getWidth(), frame.getHeight(),
                    false
            );
            WindowSettings settings = storage.load(title, defaults);

            frame.setBounds(settings.x, settings.y, settings.width, settings.height);
            try {
                frame.setIcon(settings.minimized);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
