package ballGame;

import java.awt.*;

import static java.lang.Math.toIntExact;

public class BlockGenerator {
    public int layout[][];
    public int tempLayout[][];
    public int brickWidth;
    public int brickHeight;
    public int totalrows = 10;
    public BlockGenerator(int row, int col) {
        layout = new int[col][totalrows];
        tempLayout = new int[col][totalrows];
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < row; y++) {
                layout[x][y] = y;
                if (y == 2) {
                    layout[x][y] = 2;
                }

            }
        }
        brickWidth = 50;
        brickHeight = 30;

    }


    public void draw(Graphics2D g, double spacingMultiplier) {
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout[0].length; y++) {
                if (layout[x][y] > 0) {
                    if (layout[x][y] == 1) {
                        g.setColor(Color.decode("#FF0000"));
                    }
                    if (layout[x][y] == 2) {
                        g.setColor(Color.decode("#FFFF00"));
                    }

                    g.fillRect(toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + 20, toIntExact(Math.round(y * spacingMultiplier * brickHeight)) + 10, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.decode("#FFFFFF"));
                    g.drawRect(toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + 20, toIntExact(Math.round(y * spacingMultiplier * brickHeight)) + 10, brickWidth, brickHeight);
                    g.setColor(Color.decode("#0000FF"));
                    g.drawString(Integer.toString(layout[x][y]), toIntExact(Math.round(spacingMultiplier * x * brickWidth)) + 20 + brickWidth / 2, toIntExact(Math.round(y * spacingMultiplier * brickHeight) + 10) + brickHeight / 2);


                }
            }
        }
    }

    public void shiftDown(boolean gameWon) {
        if (!gameWon) {

            for (int x = 0; x < layout.length; x++) {

                for (int y = 0; y < layout[0].length - 1; y++) {
                    tempLayout[x][y + 1] = layout[x][y];

                }

            }
            for (int x = 0; x < layout.length; x++) {

            }
            for (int x = 0; x < layout.length; x++) {

                for (int y = 0; y < layout[0].length; y++) {
                    layout[x][y] = tempLayout[x][y];

                }
            }
        }
    }

    public void BrickHit(int val, int row, int col) {
        layout[row][col] = val - 1;

    }


}
