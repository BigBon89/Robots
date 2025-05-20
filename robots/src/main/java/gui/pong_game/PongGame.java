package gui.pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class PongGame extends JPanel {
    private int points1 = 0, points2 = 0;

    private final CollisionSystem collisionSystem;
    private Platform platform1, platform2;
    private GameOverZone gameOverZone1, gameOverZone2;
    private Ball ball;

    private boolean init;
    private final Set<Integer> keysPressed = new HashSet<>();

    private final GameMode gameMode;
    private final int textScoresPositionY = 18;
    private final int ballDiam = 20;
    private final int platformPositionX = 50;
    private final int platformWidth = 20;
    private final int platformHeight = 50;

    public PongGame(GameMode gameMode) {
        collisionSystem = CollisionSystem.getInstance();
        gameOverZone1 = new GameOverZone();
        gameOverZone2 = new GameOverZone();
        init = false;
        this.gameMode = gameMode;

        this.setFocusable(true);

        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
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
            platform1 = new Platform(platformPositionX, getHeight() / 2, platformWidth, platformHeight);
            platform2 = new Platform(getWidth() - platformPositionX, getHeight() / 2, platformWidth, platformHeight);
            ball = new Ball(getWidth() / 2, getHeight() / 2, ballDiam);

            collisionSystem.add(platform1);
            collisionSystem.add(platform2);
            collisionSystem.add(ball);
            collisionSystem.add(gameOverZone1);
            collisionSystem.add(gameOverZone2);
            init = true;
        }
        gameOverZone1.resize(0, 0, platformPositionX, getHeight());
        gameOverZone2.resize(getWidth() - platformPositionX, 0, platformPositionX, getHeight());
        platform2.setPositionX(getWidth() - platformPositionX);

        if (keysPressed.contains(KeyEvent.VK_W)) {
            platform1.move(-5);
        }
        if (keysPressed.contains(KeyEvent.VK_S)) {
            platform1.move(5);
        }
        if (gameMode == GameMode.HOT_SEAT) {
            if (keysPressed.contains(KeyEvent.VK_UP)) {
                platform2.move(-5);
            }
            if (keysPressed.contains(KeyEvent.VK_DOWN)) {
                platform2.move(5);
            }
        } else {
            int aiSpeed = 0;

            if (gameMode == GameMode.PLAYER_VS_AI_DIFFICULT_1) {
                aiSpeed = 3;
            } else if (gameMode == GameMode.PLAYER_VS_AI_DIFFICULT_2) {
                aiSpeed = 5;
            }

            int ballCenterY = ball.getBounds().y + ball.getBounds().width / 2;
            int platform2CenterY = platform2.getBounds().y + platform2.getBounds().height / 2;

            if (platform2CenterY < ballCenterY - 5) {
                platform2.move(aiSpeed);
            } else if (platform2CenterY > ballCenterY + 5) {
                platform2.move(-aiSpeed);
            }
        }

        collisionSystem.screenHeight = windowHeight;

        Collidable collisionObject = ball.move();
        if (collisionObject == gameOverZone1) {
            points2++;
            ball.setPosition(getWidth() / 2, getHeight() / 2);
            ball.setVelocity(2, 0);
        } else if (collisionObject == gameOverZone2) {
            points1++;
            ball.setPosition(getWidth() / 2, getHeight() / 2);
            ball.setVelocity(2, 0);
        }
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

        if (platform1 != null) platform1.draw(g2d);
        if (platform2 != null) platform2.draw(g2d);
        if (ball != null) ball.draw(g2d);
        if (gameOverZone1 != null) gameOverZone1.draw(g2d);
        if (gameOverZone2 != null) gameOverZone2.draw(g2d);
    }
}
