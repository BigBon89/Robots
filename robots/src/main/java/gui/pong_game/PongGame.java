package gui.pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.event.KeyEvent.*;

public class PongGame extends JPanel {
    private final java.util.Timer m_timer;

    private final int m_textScoresPositionY = 18;

    private int points1 = 0;
    private int points2 = 0;

    private Platform platform1;
    private Platform platform2;
    private GameOverZone gameOverZone1;
    private GameOverZone gameOverZone2;
    private Ball ball;
    private final CollisionSystem collisionSystem;

    private boolean init;
    private final Set<Integer> keysPressed = new HashSet<>();
    public PongGame() {
        collisionSystem = CollisionSystem.getInstance();
        init = false;

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
                keysPressed.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed.remove(e.getKeyCode());
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

        if (!init) {
            platform1 = new Platform(50, getHeight() / 2, 20, 50);
            platform2 = new Platform(getWidth() - 50, getHeight() / 2, 20, 50);
            gameOverZone1 = new GameOverZone(0, 0, 50, getHeight());
            gameOverZone2 = new GameOverZone(getWidth() - 50, 0, 50, getHeight());
            ball = new Ball(100, 100, 20);

            collisionSystem.add(platform1);
            collisionSystem.add(platform2);
            collisionSystem.add(ball);
            collisionSystem.add(gameOverZone1);
            collisionSystem.add(gameOverZone2);
            init = true;
        }

        if (keysPressed.contains(KeyEvent.VK_W)) {
            platform1.move(-5);
        }
        if (keysPressed.contains(KeyEvent.VK_S)) {
            platform1.move(5);
        }
        if (keysPressed.contains(KeyEvent.VK_UP)) {
            platform2.move(-5);
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            platform2.move(5);
        }

        collisionSystem.screenHeight = windowHeight;

        Collidable collisionObject = ball.move();
        if (collisionObject == gameOverZone1) {
            points2++;
            ball.setPosition(getWidth() / 2, getHeight() / 2);
        } else if (collisionObject == gameOverZone2) {
            points1++;
            ball.setPosition(getWidth() / 2, getHeight() / 2);
        }
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawTextScores(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("Scores Player1: " + Integer.toString(points1), getWidth() / 2, m_textScoresPositionY);
        g.drawString("Scores Player2: " + Integer.toString(points2), getWidth() / 2, m_textScoresPositionY * 2);
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
        gameOverZone1.draw(g2d);
        gameOverZone2.draw(g2d);
    }
}
