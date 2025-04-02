package gui;

import javax.swing.*;
import java.util.prefs.Preferences;

public class WindowSaver {
    private final Preferences prefs;
    private final JDesktopPane desktopPane;

    public WindowSaver(JDesktopPane pane) {
        prefs = Preferences.userNodeForPackage(MainApplicationFrame.class);
        desktopPane = pane;
    }

    public void saveWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String title = frame.getTitle();
            prefs.putInt(title + "_x", frame.getX());
            prefs.putInt(title + "_y", frame.getY());
            prefs.putInt(title + "_width", frame.getWidth());
            prefs.putInt(title + "_height", frame.getHeight());
        }
    }

    public void restoreWindows() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            String title = frame.getTitle();
            int x = prefs.getInt(title + "_x", frame.getX());
            int y = prefs.getInt(title + "_y", frame.getY());
            int width = prefs.getInt(title + "_width", frame.getWidth());
            int height = prefs.getInt(title + "_height", frame.getHeight());

            frame.setBounds(x, y, width, height);
        }
    }
}
