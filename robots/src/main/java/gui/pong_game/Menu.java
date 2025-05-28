package gui.pong_game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private final JComboBox<GameMode> modeSelector;
    private final JButton startButton;

    public Menu() {
        setLayout(new FlowLayout());

        modeSelector = new JComboBox<>(GameMode.values());

        startButton = new JButton("Начать игру");

        add(modeSelector);
        add(startButton);
    }

    public GameMode getSelectedGameMode() {
        return (GameMode) modeSelector.getSelectedItem();
    }

    public void setStartAction(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}
