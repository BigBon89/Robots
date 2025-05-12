package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class PongGame extends JPanel {
    private final java.util.Timer m_timer;

    private volatile int m_ballPositionX = 50;
    private volatile int m_ballPositionY = 50;
    private final int m_ballPositionDiam = 20;
    private final int m_platformPositionX = 50;
    private volatile int m_platformPositionY = 20;
    private final int m_platformWidth = 20;
    private final int m_platformHeight = 50;

    public PongGame() {
        m_timer = new Timer("events generator", true);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setPlatformPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void setPlatformPosition(Point p) {
        m_platformPositionY = p.y;
    }

    protected void onModelUpdateEvent() {
        int newX = m_ballPositionX + 1;
        int newY = m_ballPositionY + 1;
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
        g.setColor(Color.RED);
        fillCircle(g, x, y, diam);
    }

    private void drawPlatform(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.BLUE);
        fillRect(g, x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawBall(g2d, m_ballPositionX, m_ballPositionY, m_ballPositionDiam);
        drawPlatform(g2d, m_platformPositionX, m_platformPositionY, m_platformWidth, m_platformHeight);
    }
}
