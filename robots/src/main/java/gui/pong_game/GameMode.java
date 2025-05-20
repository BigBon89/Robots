package gui.pong_game;

public enum GameMode {
    HOT_SEAT("Hot Seat"),
    PLAYER_VS_AI_DIFFICULT_1("AI Легкий"),
    PLAYER_VS_AI_DIFFICULT_2("AI Сложный");

    private final String displayName;

    GameMode(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
