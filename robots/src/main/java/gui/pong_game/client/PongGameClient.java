package gui.pong_game.client;

import gui.pong_game.Ball;
import gui.pong_game.Command;
import gui.pong_game.GameState;
import gui.pong_game.Platform;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class PongGameClient extends JPanel {
    private GameState state = new GameState();
    private Command command = new Command();
    private Ball ball = new Ball(0, 0,0, 0);
    private Platform platform1 = new Platform(0, 0,20, 50);
    private Platform platform2 = new Platform(0, 0,20, 50);
    private int points1 = 0, points2 = 0;
    private final int textScoresPositionY = 18;

    public PongGameClient() throws IOException {
        Socket socket = new Socket("localhost", 9001);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        this.setFocusable(true);

        java.util.Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    out.reset();
                    out.writeObject(command);
                    state = (GameState)in.readObject();
                    ball.positionX = state.ballX;
                    ball.positionY = state.ballY;
                    ball.diam = state.diam;
                    platform1.positionY = state.platform1Y;
                    platform2.positionY = state.platform2Y;
                    platform1.positionX = state.platform1X;
                    platform2.positionX = state.platform2X;
                    points1 = state.score1;
                    points2 = state.score2;
                    repaint();
                } catch (Exception e) {
                    Logger.debug(e.getMessage());
                }
                onRedrawEvent();
            }
        }, 0, 15);
        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboardFocusManager.addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_W) command.upPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_S) command.downPressed = true;
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_W) command.upPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_S) command.downPressed = false;
            }
            return false;
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTextScores(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("Scores Player1: " + Integer.toString(points1), getWidth() / 2, textScoresPositionY);
        g.drawString("Scores Player2: " + Integer.toString(points2), getWidth() / 2, textScoresPositionY * 2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        drawBackground(g2d);
        drawTextScores(g2d);

        ball.draw(g2d);
        platform1.draw(g2d);
        platform2.draw(g2d);
    }
}
