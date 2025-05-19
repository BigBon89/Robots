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

    private final int m_textScoresPositionX = 60;
    private final int m_textScoresPositionY = 18;

    private int m_points = 0;

    private final Platform platform1;
    private final Platform platform2;
    private final Ball ball;
    private final CollisionSystem collisionSystem;

    public PongGame() {
        collisionSystem = CollisionSystem.getInstance();
        platform1 = new Platform(50, 50, 20, 50);
        platform2 = new Platform(350, 50, 20, 50);
        ball = new Ball(100, 100, 20);

        collisionSystem.add(platform1);
        collisionSystem.add(platform2);
        collisionSystem.add(ball);

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
                    platform1.move(-10);
                }
                if (e.getKeyCode() == VK_S) {
                    platform1.move(10);
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

    protected void onModelUpdateEvent() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        if (windowWidth <= 0 || windowHeight <= 0) {
            return;
        }
        collisionSystem.screenWidth = windowWidth;
        collisionSystem.screenHeight = windowHeight;

        ball.move();
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTextScores(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("Scores: " + Integer.toString(m_points), getWidth() - m_textScoresPositionX, m_textScoresPositionY);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        drawBackground(g2d);
        drawTextScores(g2d);

        platform1.draw(g2d);
        platform2.draw(g2d);
        ball.draw(g2d);
    }
}
