package gui.pong_game.server;

import gui.pong_game.Command;
import gui.pong_game.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Command command;

    public ClientHandler(Socket socket) throws IOException, IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        command = new Command();
    }

    public boolean readCommand() {
        try {
            command = (Command)in.readObject();
            return true;
        } catch (Exception e) {
            close();
            return false;
        }
    }

    public void sendGameState(GameState gameState) {
        try {
            out.reset();
            out.writeObject(gameState);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
