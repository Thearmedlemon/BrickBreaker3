package ballGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static java.lang.Math.*;


public class GameContent extends JPanel implements ActionListener, KeyListener {
    private int dropCounter = 0;
    private boolean playing = true;
    public Ball[] Balls;
    private int score = 0;
    private boolean gameWon = false;
    private int delay = 5;
    private Timer timeManager;
    private Timer stall;

    private int brickWidth = 50;
    private int brickHeight = 30;
    private boolean gameOver;
    private int yConst = 500;
    private int arrowBaseX = 300;
    private int arrowBaseY = 425 + yConst;
    private int arrowTopX = 302;
    private int arrowTopY = 375 + yConst;
    private int brickCount;
    private double spacingMultiplier = 1.5;
    private double[] ballXPos = new double[25];
    private double[] ballYPos = new double[25];
    private double[] lastBallXPos = new double[25];
    private double[] lastBallYPos = new double[25];
    private double[] ballXSpeed = new double[25];
    private double[] ballYSpeed = new double[25];
    private Ball[] BallsArray = new Ball[25];
    private int currentBallCount;
    private String ballColour = "#FFFF00";
    private int arrowSpeed = 5;
    private double ballSize = 10;
    private int multiplier = 1;
    private double multiplierBounceCount = 5;
    private double multiplierBounceDirection = .5;

    private boolean powerupActive = false;
    private int powerupX;
    private int powerupY;
    private int gravity = 1;

    private int rows = 8;
    private int columns = 8;

    private int tempCount = 0;

    private boolean ballExists = false;
    private BlockGenerator Level1;

    private double theta;


    GameContent() {


        brickCount = rows * columns;
        Level1 = new BlockGenerator(rows, columns);
        Timer stall = new Timer(5, this);
        stall.setRepeats(false);
        stall.setDelay(200);
        timeManager = new Timer(delay, this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timeManager.start();

        currentBallCount = 1;


        for (int i = 0; i < 25; i++) {

            BallsArray[i] = new Ball(ballXPos[i], ballYPos[i], ballSize, Color.decode(ballColour), ballXSpeed[i], ballYSpeed[i], false);
        }


        //ballAssign();
    }

//    public void ballAssign(){
//
//        for (int i=0; i<25;i++){
//
//
//
//
//
//        }
//    }




    @Override
    public void actionPerformed(ActionEvent e) {
        timeManager.start();
        if (!gameWon || !gameOver) {
            if (multiplierBounceCount == 1 || multiplierBounceCount == 20) {
                multiplierBounceDirection = -multiplierBounceDirection;
            }
            multiplierBounceCount = multiplierBounceCount + multiplierBounceDirection;

            for (int k = 0; k < 25; k++) {


                if (BallsArray[k].getActive()) {
                    lastBallXPos[k] = ballXPos[k];
                    lastBallYPos[k] = ballYPos[k];
                    ballXPos[k] += ballXSpeed[k];
                    ballYPos[k] += ballYSpeed[k];
                    if (ballXPos[k] <= 0) {
                        ballXSpeed[k] = -ballXSpeed[k];
                    }

                    if (ballXPos[k] >= 590) {
                        ballXSpeed[k] = -ballXSpeed[k];
                    }

                    if (ballYPos[k] >= 480 + yConst) {
                        //hits the bottom of the screen
                        tempCount++;
                        BallsArray[k].setActive(false);

                        if (tempCount == currentBallCount) {


                            arrowBaseX = toIntExact(Math.round(ballXPos[k]));
                            arrowTopX = arrowBaseX + 3;
                            multiplier = 1;
                            dropCounter++;
                            tempCount = 0;
                        }
                        if (dropCounter == 3) {
                            Level1.shiftDown(gameWon);

                            gameOver = Level1.gameWonCheck();


                            dropCounter = 0;
                        }

                    }


                }

                if (ballYPos[k] <= 0) {
                    ballYSpeed[k] = -ballYSpeed[k];
                }

                for (int x = 0; x < Level1.layout.length; x++) {
                    for (int y = 0; y < Level1.layout[0].length; y++) {
                        if (Level1.layout[x][y] > 0) {

                            int brickWidth = Level1.brickWidth;
                            int brickHeight = Level1.brickHeight;

                            int brickX = toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + 20;
                            int brickY = toIntExact(Math.round(y * spacingMultiplier * brickHeight));


                            Rectangle BlockHitBox = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                            Rectangle BallHitBox = new Rectangle(toIntExact(Math.round(ballXPos[k])), toIntExact(Math.round(ballYPos[k])), toIntExact(Math.round(ballSize)), toIntExact(Math.round(ballSize)));

                            if (BallHitBox.intersects(BlockHitBox)) {
                                if (Level1.layout[x][y] > 0) {
                                    Level1.BrickHit(Level1.layout[x][y], x, y);

                                    Random rand = new Random();
                                    int drop = rand.nextInt(2);
                                    if (drop == 1 && !powerupActive) {
                                        ItemSpawn(x, y);
                                    }

                                    if (Level1.layout[x][y] == 0) {
                                        brickCount--;
                                        multiplier++;


                                        if (brickCount == 0) {
                                            gameWon = true;
                                        }

                                    }
                                    if (brickCount == 0) {
                                        gameWon = true;
                                    }
                                    score = score + 5 * multiplier;

                                } else {
                                    score = score + multiplier;
                                }

                                //flip on x
                                if (lastBallXPos[k] <= BlockHitBox.x || lastBallXPos[k] >= BlockHitBox.x + BlockHitBox.width) {

                                    ballXSpeed[k] = -ballXSpeed[k];
                                    if (lastBallXPos[k] <= BlockHitBox.x) {
                                        ballXPos[k] = BlockHitBox.x - 10;
                                    } else {
                                        ballXPos[k] = BlockHitBox.x + BlockHitBox.width + 10;
                                    }

                                } else {
                                    ballYSpeed[k] = -ballYSpeed[k];
                                    if (lastBallYPos[k] <= BlockHitBox.y) {
                                        ballYPos[k] = BlockHitBox.y - 10;
                                    } else {
                                        ballYPos[k] = BlockHitBox.y + BlockHitBox.height + 10;
                                    }
                                }

                            }

                            Rectangle PowerUpHitBox = new Rectangle(powerupX, powerupY, toIntExact(Math.round(ballSize)) * 2, toIntExact(Math.round(ballSize)) * 2);
                            if (BallHitBox.intersects(PowerUpHitBox) && powerupActive) {
                                powerupActive = false;
                                tempCount++;

                                if (currentBallCount < 25) {
                                    currentBallCount++;
                                } else {
                                    score = score + 500 * multiplier;
                                }
                            }

                        }
                    }
                }

            }
            powerupY = powerupY + gravity;


        }

        repaint();

    }


    private void ItemSpawn(int x, int y) {
        if (!powerupActive) {
            powerupX = (toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + brickWidth / 2);
            powerupY = (toIntExact(Math.round(y * spacingMultiplier * brickHeight)) + brickHeight / 2);
            powerupActive = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void paint(Graphics g) {

        if (!gameWon || !gameOver) {
            Graphics2D g2d = (Graphics2D) g;

            //settingBackground
            g.setColor(Color.BLACK);
            g.fillRect(1, 1, 800, 500 + yConst);

            //setting Borders
            g.setColor(Color.ORANGE);
            g.fillRoundRect(595, 1, 5, 500 + yConst, 3, 3);
            g.fillRoundRect(595, 350 + yConst, 200, 5, 3, 3);

            //score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Comic Sans", Font.BOLD, 30));
            g.drawString("Score:" + score, 610, 50);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 15));
            g.drawString("Number of Balls:" + currentBallCount, 610, 830);
            if (multiplier != 1) {
                g.setFont(new Font("Papyrus", Font.PLAIN, 10 + toIntExact(Math.round(+1 * multiplierBounceCount))));
                g.drawString("Multiplier: x" + multiplier, 610, 100);

            }

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
//        for (currentBallCount = 0; currentBallCount < 25; currentBallCount++) {
//
//
//        }//

            // block generators
            Level1.draw((Graphics2D) g, spacingMultiplier);

            //powerup check

            if (powerupActive) {
                if (powerupY >= 480 + yConst) {
                    powerupActive = false;
                }
                if (powerupActive) {
                    g.setColor(Color.CYAN);
                    g.fillOval(toIntExact(Math.round(powerupX)), toIntExact(Math.round(powerupY)), toIntExact(Math.round(ballSize * 2)), toIntExact(Math.round(ballSize * 2)));
                    g.setColor(Color.BLACK);
                    g.drawString("+", powerupX + 5, powerupY + 16);
                }
            }


            // ballColour = meme.getColour();
            for (int k = 0; k < 25; k++) {
                if (BallsArray[k].getActive()) {
                    g.setColor(Color.decode(ballColour));
                    g.fillOval(toIntExact(Math.round(ballXPos[k])), toIntExact(Math.round(ballYPos[k])), toIntExact(Math.round(ballSize)), toIntExact(Math.round(ballSize)));
                }
            }
        }
        if (gameOver || gameWon) {
            g.setColor(Color.BLACK);
            g.fillRect(1, 1, 800, 500 + yConst);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Comic Sans", Font.BOLD, 40));
            g.drawString("Game Over.", 250, 400);
            g.drawString("You scored:" + score, 250, 500);
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            arrowTopX = 302;
            arrowTopY = 375 + yConst;
            arrowBaseX = 299;
            ballExists = false;

            for (int k = 0; k < currentBallCount; k++) {
                BallsArray[k].setActive(false);
            }


            ItemSpawn(1, 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            Level1.shiftDown(gameWon);

            gameOver = Level1.gameWonCheck();


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
            if (arrowTopY <= 500) {
                arrowTopY = 500;
            } else {
                moveUp();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (arrowTopY >= 915) {
                arrowTopY = 915;
            } else {
                moveDown();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {


            for (int k = 0; k < currentBallCount; k++) {
                for (int x = 0; x < 200; x++) {

                    if (!BallsArray[k].getActive()) {
                        double dy = arrowTopY - arrowBaseY;
                        double dx = arrowTopX - arrowBaseX - 2;

                        theta = (Math.PI - (Math.asin((dy) / (sqrt((dy * dy) + (dx * dx))))));
                        if (dx <= 0) {
                            ballXSpeed[k] = 5 * cos(theta);
                            ballYSpeed[k] = 5 * sin(theta);
                        } else {
                            ballXSpeed[k] = -5 * cos(theta);
                            ballYSpeed[k] = 5 * sin(theta);
                        }
                        ballXPos[k] = arrowBaseX + k * 2 * toIntExact(Math.round(ballXSpeed[k]));
                        ballYPos[k] = arrowBaseY + k * 2 * toIntExact(Math.round(ballYSpeed[k]));


                        BallsArray[k].setActive(true);
                        ballExists = true;


                    }

                }

            }
        }
    }


    private void moveRight() {
        playing = true;
        arrowTopX += arrowSpeed;
    }

    private void moveLeft() {
        playing = true;
        arrowTopX -= arrowSpeed;
    }

    private void moveUp() {
        playing = true;
        arrowTopY -= arrowSpeed;
    }

    private void moveDown() {
        playing = true;
        arrowTopY += arrowSpeed;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
