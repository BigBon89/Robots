package gui;

import gui.pong_game.GameMode;
import gui.pong_game.Menu;
import gui.pong_game.PongGame;

import java.awt.BorderLayout;

import javax.swing.*;

@SavableWindow
public class GameWindow extends JInternalFrame {
    private PongGame m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);

        Menu menu = new Menu();
        getContentPane().add(menu);
        pack();

        menu.setStartAction(e -> {
            getContentPane().remove(menu);

            GameMode selectedMode = menu.getSelectedGameMode();

            m_visualizer = new PongGame(selectedMode);
            JPanel gamePanel = new JPanel(new BorderLayout());
            gamePanel.add(m_visualizer);
            getContentPane().add(gamePanel);
            revalidate();
            m_visualizer.requestFocusInWindow();
        });
    }
}
