package gui.pong_game;

public enum GameMode {
    HOT_SEAT("Hot Seat", 3),
    PLAYER_VS_AI_DIFFICULT_1("AI Легкий", 3, 3),
    PLAYER_VS_AI_DIFFICULT_2("AI Сложный", 5, 5);

    private final String displayName;
    private final int ballSpeed;
    private final int aiPlatformSpeed;

    GameMode(String displayName, int ballSpeed) {
        this.displayName = displayName;
        this.ballSpeed = ballSpeed;
        this.aiPlatformSpeed = 0;
    }

    GameMode(String displayName, int ballSpeed, int aiPlatformSpeed) {
        this.displayName = displayName;
        this.ballSpeed = ballSpeed;
        this.aiPlatformSpeed = aiPlatformSpeed;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public int getAiPlatformSpeed() {
        return aiPlatformSpeed;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
