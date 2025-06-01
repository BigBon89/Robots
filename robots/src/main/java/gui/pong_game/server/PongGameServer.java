package gui.pong_game.server;

import gui.pong_game.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class PongGameServer {
    private final int port = 9001;
    private int points1 = 0, points2 = 0;

    private CollisionSystem collisionSystem;
    private GameOverZone gameOverZone1;
    private GameOverZone gameOverZone2;
    private Platform platform1, platform2;
    private Ball ball;

    private boolean init;
    private final Set<Integer> keysPressed = new HashSet<>();

    private GameMode gameMode;
    private final int ballDiam = 20;
    private final int platformPositionX = 50;
    private final int platformWidth = 20;
    private final int platformHeight = 50;
    private final int platformPlayerSpeed = 5;
    private final int width = 800;
    private final int height = 600;
    private GameState gameState;
    private Command command;

    public static void main(String[] args) throws IOException {
        new PongGameServer().start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = serverSocket.accept();
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

        gameState = new GameState();
        command = new Command();
        gameMode = GameMode.PLAYER_VS_AI_DIFFICULT_2;
        collisionSystem = CollisionSystem.getInstance();
        gameOverZone1 = new GameOverZone();
        gameOverZone2 = new GameOverZone();
        init = false;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    command = (Command)in.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handle();
                gameState.ballX = ball.positionX;
                gameState.ballY = ball.positionY;
                gameState.diam = ball.diam;
                gameState.platform1Y = platform1.positionY;
                gameState.platform2Y = platform2.positionY;
                gameState.platform1X = platform1.positionX;
                gameState.platform2X = platform2.positionX;
                gameState.score1 = points1;
                gameState.score2 = points2;
                try {
                    out.reset();
                    out.writeObject(gameState);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }, 0, 15);
    }

    private void handle() {
        if (!init) {
            platform1 = new Platform(platformPositionX, height / 2, platformWidth, platformHeight);
            platform2 = new Platform(width - platformPositionX, height / 2, platformWidth, platformHeight);
            ball = new Ball(width / 2, height / 2, ballDiam, gameMode.getBallSpeed());

            collisionSystem.add(platform1);
            collisionSystem.add(platform2);
            collisionSystem.add(ball);
            collisionSystem.add(gameOverZone1);
            collisionSystem.add(gameOverZone2);
            init = true;
        }
        gameOverZone1.resize(0, 0, platformPositionX, height);
        gameOverZone2.resize(width - platformPositionX, 0, platformPositionX, height);
        platform2.setPositionX(width - platformPositionX);

        if (command.upPressed) {
            platform1.move(-platformPlayerSpeed);
        }
        if (command.downPressed) {
            platform1.move(platformPlayerSpeed);
        }
        if (gameMode == GameMode.HOT_SEAT) {
            if (keysPressed.contains(KeyEvent.VK_UP)) {
                platform2.move(-platformPlayerSpeed);
            }
            if (keysPressed.contains(KeyEvent.VK_DOWN)) {
                platform2.move(platformPlayerSpeed);
            }
        } else {
            CircleBound ballBound = (CircleBound)ball.getBounds();
            RectBound platform2Bound = (RectBound)platform2.getBounds();
            Rectangle platform2Rect = platform2Bound.getRect();

            int platform2CenterY = platform2Rect.y + platform2Rect.height / 2;

            int deadZoneOffset = 5;
            if (platform2CenterY < ballBound.getCenterY() - deadZoneOffset) {
                platform2.move(gameMode.getAiPlatformSpeed());
            } else if (platform2CenterY > ballBound.getCenterY() + deadZoneOffset) {
                platform2.move(-gameMode.getAiPlatformSpeed());
            }
        }

        collisionSystem.screenHeight = height;

        Collidable collisionObject = ball.move();
        if (collisionObject == gameOverZone1) {
            points2++;
            ball.setPosition(width / 2, height / 2);
            ball.resetVelocity();
        } else if (collisionObject == gameOverZone2) {
            points1++;
            ball.setPosition(width / 2, height / 2);
            ball.resetVelocity();
        }
    }
}
