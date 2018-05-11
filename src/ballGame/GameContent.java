package ballGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameContent extends JPanel implements ActionListener, KeyListener {

    private boolean playing = false;
    private int brickCount = 25;
    private int score = 0;

    private int delay = 5;
    private Timer timeManager;

    private int arrowBaseX = 300;
    private int arrowBaseY = 425;
    private int arrowTopX = 302;
    private int arrowTopY = 375;

    private double[] ballXPos;
    private double[] ballYPos;
    private double[] ballXSpeed;
    private double[] ballYSpeed;


    public GameContent() {

        timeManager = new Timer(delay, this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timeManager.start();


    }

    private int speed = 10;


    @Override
    public void actionPerformed(ActionEvent e) {
        timeManager.start();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void paint(Graphics g) {
        //settingBackground
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 800, 500);

        //setting Borders
        g.setColor(Color.ORANGE);
        g.fillRoundRect(595, 1, 5, 500, 3, 3);
        g.fillRoundRect(595, 350, 200, 5, 3, 3);


        //ArrowTop
        g.setColor(Color.GREEN);
        g.fillRect(arrowTopX, arrowTopY, 6, 6);

        //ArrowBottom
        g.setColor(Color.GREEN);
        g.fillRect(arrowBaseX, arrowBaseY, 10, 10);

        //connecting line
        g.drawLine(arrowTopX + 3, arrowTopY + 3, arrowBaseX + 5, arrowBaseY + 5);


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            arrowTopX = 302;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (arrowTopX >= 589) {
                arrowTopX = 589;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (arrowTopX <= 0) {
                arrowTopX = 0;
            } else {
                moveLeft();
            }
        }


    }

    public void moveRight() {
        playing = true;
        arrowTopX += speed;
    }

    public void moveLeft() {
        playing = true;
        arrowTopX -= speed;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}