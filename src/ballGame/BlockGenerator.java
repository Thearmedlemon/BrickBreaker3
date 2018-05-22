package ballGame;

import java.awt.*;

import static java.lang.Math.toIntExact;

class BlockGenerator {
    private boolean gameWonvar;
    int layout[][];
    int brickWidth;
    int brickHeight;
    private int tempLayout[][];
    private Color colourVal = new Color(0xFF, 0xFF, 0xFF);
    private int red;
    private int green;
    private int blue;

    BlockGenerator(int row, int col) {
        int totalRows = 20;
        layout = new int[col][totalRows];
        tempLayout = new int[col][totalRows];
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y <= row; y++) {
                layout[x][row - y] = y;


            }
        }
        brickWidth = 50;
        brickHeight = 30;

    }

    private String complimentaryColourFinder(int r, int g, int b) {
        float[] hsb = new float[3];
        int complimentary;
        String hex;
        Color.RGBtoHSB(r, g, b, hsb);
        hsb[0] = (hsb[0] + 180) % 360;


        complimentary = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        hex = Integer.toHexString((complimentary & 0xFFFFFF) ^ 0xFFFFFF);

        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex;


        return (hex);

    }


    void draw(Graphics2D g, double spacingMultiplier) {
        for (int x = 0; x < layout.length; x++) {
            for (int y = layout[0].length - 1; y > 0; y--) {
                if (layout[x][y] > 0) {
                    if (layout[x][y] == 1) {
                        red = 0xFF;
                        green = 0x00;
                        blue = 0x00;


                    }
                    if (layout[x][y] == 2) {
                        red = 0xFF;
                        green = 0xFF;
                        blue = 0x00;

                    }
                    if (layout[x][y] == 3) {
                        red = 0x00;
                        green = 0xFF;
                        blue = 0xFF;

                    }
                    if (layout[x][y] == 4) {
                        red = 0x80;
                        green = 0x00;
                        blue = 0x00;

                    }
                    if (layout[x][y] == 5) {
                        red = 0xFF;
                        green = 0xA5;
                        blue = 0x00;

                    }

                    if (layout[x][y] == 6) {
                        red = 0xFF;
                        green = 0x69;
                        blue = 0xb4;

                    }
                    if (layout[x][y] == 7) {
                        red = 0x80;
                        green = 0x00;
                        blue = 0x80;

                    }
                    if (layout[x][y] == 8) {
                        red = 0x00;
                        green = 0xFF;
                        blue = 0x00;

                    }

                    colourVal = new Color(red, green, blue);
                    Color complimentaryColorVal = Color.decode(complimentaryColourFinder(colourVal.getRed(), colourVal.getGreen(), colourVal.getBlue()));


                    g.setColor(colourVal);
                    g.fillRect(toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + 18, toIntExact(Math.round(y * spacingMultiplier * brickHeight)) + 10, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.decode("#FFFFFF"));
                    g.drawRect(toIntExact(Math.round(x * spacingMultiplier * brickWidth)) + 18, toIntExact(Math.round(y * spacingMultiplier * brickHeight)) + 10, brickWidth, brickHeight);
                    g.setColor(complimentaryColorVal);
                    g.setFont(new Font("Consolas", Font.ITALIC, 20));
                    g.drawString(Integer.toString(layout[x][y]), toIntExact(Math.round(spacingMultiplier * x * brickWidth)) + 15 + brickWidth / 2, toIntExact(Math.round(y * spacingMultiplier * brickHeight) + 15) + brickHeight / 2);


                }
            }
        }
    }

    void shiftDown(boolean gameWon) {
        if (!gameWon) {

            for (int x = 0; x < layout.length; x++) {

                System.arraycopy(layout[x], 0, tempLayout[x], 1, layout[0].length - 1);

            }

            for (int x = 0; x < layout.length; x++) {

                System.arraycopy(tempLayout[x], 0, layout[x], 0, layout[0].length);
            }
            for (int[] aLayout : layout) {
                if (aLayout[19] != 0) {
                    gameWonvar = true;
                }
            }
        }
    }

    void BrickHit(int val, int row, int col) {
        layout[row][col] = val - 1;

    }

    boolean gameWonCheck() {
        return gameWonvar;
    }


}
