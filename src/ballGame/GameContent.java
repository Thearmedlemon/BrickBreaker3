package ballGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Math.*;


public class GameContent extends JPanel implements ActionListener, KeyListener {

    private boolean playing = false;
    public Ball[] Balls;
    private int score = 0;

    private int delay = 5;
    private Timer timeManager;

    private int arrowBaseX = 300;
    private int arrowBaseY = 425;
    private int arrowTopX = 302;
    private int arrowTopY = 375;
    private int brickCount;
    private double ballsize = 10;
    private double ballXPos;
    private double ballYPos;
    private double ballXSpeed = 5;
    private double ballYSpeed = 5;
    private int currentBallCount;
    private String ballColour = "#FFFF00";
    private int arrowSpeed = 5;

    private boolean ballExists = false;
//    private double arrowLeftHeadX;
//    private double arrowLeftHeadY;
//    private double arrowRightHeadX;
//    private double arrowRightHeadY;
//    private double dy;
//    private double dx;
private double theta;
//    private double arrowHeadAngle;
//    private double newDirectionLeft;
//    private double newDirectionRight;

    public GameContent() {


        timeManager = new Timer(delay, this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timeManager.start();

        currentBallCount = 1;

        Balls = new Ball[currentBallCount];
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        timeManager.start();
        if (ballExists) {
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            if (ballXPos <= 0) {
                ballXSpeed = -ballXSpeed;
            }

            if (ballXPos >= 590) {
                ballXSpeed = -ballXSpeed;
            }

            if (ballYPos >= 480) {
                ballYSpeed = -ballYSpeed;
            }

            if (ballYPos <= 0) {
                ballYSpeed = -ballYSpeed;
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

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
        g.setColor(Color.GREEN);
        g.drawLine(arrowTopX + 3, arrowTopY + 3, arrowBaseX + 5, arrowBaseY + 5);

        //ball checks
        for (currentBallCount = 0; currentBallCount < 25; currentBallCount++) {


        }

        //Ball meme = new Ball(100, 100, 10, "#FFFF00", 5, 5);


        // ballColour = meme.getColour();
        if (ballExists) {
            g.setColor(Color.decode(ballColour));
            g.fillOval(toIntExact(Math.round(ballXPos)), toIntExact(Math.round(ballYPos)), toIntExact(Math.round(ballsize)), toIntExact(Math.round(ballsize)));
        } else {

        }
    }


//        //arrowhead calculations
//        dy=arrowTopY+3-arrowBaseY+5;
//        dx=arrowTopX+3-arrowBaseX+5;
//        System.out.println("theta" + theta + " dy= " + dy + " dx= " + dx);
//        arrowHeadAngle =  Math.asin(1/-42);
//        System.out.println("aHA = " + arrowHeadAngle);
//
//        theta= 6.0;
//
//        //arrowHeadLeft
//        newDirectionLeft = (theta+arrowHeadAngle);
//        arrowLeftHeadX=(arrowTopX + dx* Math.cos(theta) - dy * Math.sin(theta));
//        arrowLeftHeadY=(arrowTopY + dy* Math.cos(theta) + dx * Math.sin(theta));
//
//        arrowLeftHeadX = arrowTopX - (arrowTopX - arrowLeftHeadX) * (40/100);
//        arrowLeftHeadY = arrowTopY - (arrowTopY - arrowLeftHeadY) * (40/100);
//        g.drawLine((toIntExact(Math.round(arrowLeftHeadX))),(toIntExact(Math.round(arrowLeftHeadY))),arrowTopX+3,arrowTopY+3);
//
//        //arrowHeadRight
//        newDirectionRight = (theta-arrowHeadAngle);
//        arrowRightHeadX=arrowTopX + dx * Math.cos(-1*theta) - dy * Math.sin(-1*theta);
//        arrowRightHeadY=arrowTopY + dy * Math.cos(-1*theta) + dx * Math.sin(-1*theta);
//
//        arrowRightHeadX = arrowTopX - (arrowTopX - arrowRightHeadX) * .5;
//        arrowRightHeadY = arrowTopY - (arrowTopY - arrowRightHeadY) * .5;
//        g.drawLine(toIntExact(Math.round(arrowRightHeadX)),(toIntExact(Math.round(arrowRightHeadY))),arrowTopX+3,arrowTopY+3);





    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            arrowTopX = 302;
            arrowTopY = 375;
            ballExists = false;

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

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (arrowTopY <= 375) {
                arrowTopY = 375;
            } else {
                moveUp();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (arrowTopY >= 415) {
                arrowTopY = 415;
            } else {
                moveDown();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (ballExists) {
            } else {
                double dy = arrowTopY - arrowBaseY;
                double dx = arrowTopX - arrowBaseX;

                theta = (Math.PI - (Math.asin((dy) / (sqrt((dy * dy) + (dx * dx))))));
                if (dx <= 0) {
                    ballXSpeed = 5 * cos(theta);
                    ballYSpeed = 5 * sin(theta);
                } else {
                    ballXSpeed = -5 * cos(theta);
                    ballYSpeed = 5 * sin(theta);
                }
                ballXPos = arrowTopX;
                ballYPos = arrowTopY;


                createBall();
            }
        }

    }

    public void createBall() {
        ballExists = true;

    }

    public void moveRight() {
        playing = true;
        arrowTopX += arrowSpeed;
    }

    public void moveLeft() {
        playing = true;
        arrowTopX -= arrowSpeed;
    }

    public void moveUp() {
        playing = true;
        arrowTopY -= arrowSpeed;
    }

    public void moveDown() {
        playing = true;
        arrowTopY += arrowSpeed;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
