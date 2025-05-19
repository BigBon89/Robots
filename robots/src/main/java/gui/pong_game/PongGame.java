package gui.pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.event.KeyEvent.*;

public class PongGame extends JPanel {
    private final java.util.Timer m_timer;

    private int m_ballPositionX = 100;
    private int m_ballPositionY = 100;
    private final int m_ballDiam = 20;
    private int m_ballVelocityX = 2;
    private int m_ballVelocityY = 2;

    private final int m_platformPlayer1PositionX = 50;
    private int m_platformPlayer1PositionY = 50;
    private final int m_platformPlayer2PositionX = 150;
    private int m_platformPlayer2PositionY = 50;
    private final int m_platformWidth = 20;
    private final int m_platformHeight = 50;

    private final int m_textScoresPositionX = 60;
    private final int m_textScoresPositionY = 18;

    private int m_points = 0;

    private Platform platform1;
    private Platform platform2;

    public PongGame() {
        platform1 = new Platform(50, 50, 20, 50);
        platform2 = new Platform(150, 50, 20, 50);

        this.setFocusable(true);

        m_timer = new Timer("events generator", true);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
                onRedrawEvent();
            }
        }, 0, 15);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == VK_W) {
                    changePlatformPosition(-10);
                }
                if (e.getKeyCode() == VK_S) {
                    changePlatformPosition(10);
                }
                if (e.getKeyCode() == VK_UP) {
                    platform2.move(-10);
                }
                if (e.getKeyCode() == VK_DOWN) {
                    platform2.move(10);
                }
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void changePlatformPosition(int number) {
        m_platformPlayer1PositionY += number;
    }

    protected void onModelUpdateEvent() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        if (windowWidth <= 0 || windowHeight <= 0) {
            return;
        }

        int radius = m_ballDiam / 2;
        int newX = m_ballPositionX + m_ballVelocityX;
        int newY = m_ballPositionY + m_ballVelocityY;

        if (newX + radius >= windowWidth || newX - radius <= 0) {
            m_ballVelocityX = -m_ballVelocityX;
        }

        if (newY + radius >= windowHeight || newY - radius <= 0) {
            m_ballVelocityY = -m_ballVelocityY;
        }

        boolean inPlatformX = newX - m_ballDiam / 2 <= m_platformPlayer1PositionX + m_platformWidth / 2 &&
                newX + m_ballDiam / 2  >= m_platformPlayer1PositionX - m_platformWidth / 2;
        boolean inPlatformY = newY + m_ballDiam / 2 >= m_platformPlayer1PositionY - m_platformHeight / 2 &&
                newY - m_ballDiam / 2 <= m_platformPlayer1PositionY + m_platformHeight / 2;

        if (inPlatformX && inPlatformY && m_ballVelocityX < 0) {
            m_points++;
            m_ballVelocityX = 2 + (int)(Math.random() * 4);
            m_ballVelocityY = Integer.signum(m_ballVelocityY) * (2 + (int)(Math.random() * 4));
        }

        if (newX < m_platformPlayer1PositionX - m_platformWidth / 2 && !inPlatformY) {
            m_points = 0;
            resetBall();
            return;
        }

        m_ballPositionX = newX;
        m_ballPositionY = newY;
    }

    private static void fillCircle(Graphics g, int centerX, int centerY, int diam) {
        g.fillOval(centerX - diam / 2, centerY - diam / 2, diam, diam);
    }

    private static void fillRect(Graphics g, int centerX, int centerY, int width, int height) {
        g.fillRect(centerX - width / 2, centerY - height / 2, width, height);
    }

    private void drawBall(Graphics2D g, int x, int y, int diam) {
        g.setColor(Color.WHITE);
        fillCircle(g, x, y, diam);
    }

    private void drawPlatform(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.WHITE);
        fillRect(g, x, y, width, height);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTextScores(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("Scores: " + Integer.toString(m_points), getWidth() - m_textScoresPositionX, m_textScoresPositionY);
    }

    private void resetBall() {
        m_ballPositionX = 100;
        m_ballPositionY = 100;
        m_ballVelocityX = 2;
        m_ballVelocityY = 2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        drawBackground(g2d);
        drawBall(g2d, m_ballPositionX, m_ballPositionY, m_ballDiam);
        drawPlatform(g2d, m_platformPlayer1PositionX, m_platformPlayer1PositionY, m_platformWidth, m_platformHeight);
        drawTextScores(g2d);

        platform2.draw(g2d);
    }
}
