package gui;

import gui.pong_game.GameMode;
import gui.pong_game.Menu;
import gui.pong_game.PongGame;
import gui.pong_game.client.PongGameClient;
import log.Logger;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.*;

@SavableWindow
public class GameWindow extends JInternalFrame {
    private JPanel m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);

        Menu menu = new Menu();
        getContentPane().add(menu);
        pack();

        menu.setStartAction(e -> {
            getContentPane().remove(menu);

            GameMode selectedMode = menu.getSelectedGameMode();

            try {
                m_visualizer = new PongGameClient();
            } catch (IOException ex) {
                Logger.debug(ex.getMessage());
            }
            JPanel gamePanel = new JPanel(new BorderLayout());
            gamePanel.add(m_visualizer);
            getContentPane().add(gamePanel);
            revalidate();
            m_visualizer.requestFocusInWindow();
        });
    }
}
