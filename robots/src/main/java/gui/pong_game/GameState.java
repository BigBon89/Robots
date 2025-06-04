package gui.pong_game;

import java.io.Serializable;

public class GameState implements Serializable {
    public int ballX, ballY, diam;
    public int platform1X, platform2X;
    public int platform1Y, platform2Y;
    public int score1, score2;
}
