package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayManager {
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static List<Block> staticBlocks = new ArrayList<Block>();
    public boolean gameOver;
    public int level = 1;
    public int lines;
    public int score;

    public static int dropInterval = 60;

    public PlayManager() {

        left_x = GamePanel.WIDTH/2 - WIDTH/2;
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + WIDTH/2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // starting mino
        currentMino = getMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = getMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

    }

    public Mino getMino() {
        int random = new Random().nextInt(7);
        switch (random) {
            case 0 : return new Mino_L1();
            case 1 : return new Mino_L2();
            case 2 : return new Mino_Bar();
            case 3 : return new Mino_Square();
            case 4 : return new Mino_T();
            case 5 : return new Mino_Z1();
            case 6 : return new Mino_Z2();
        }
        return null;
    }

    public void update() {

        if (currentMino.active == false) {

            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // check to over the game
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                GamePanel.sfx.play(2, false);
                gameOver = true;
            }

            currentMino.deactivating = false;
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = getMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // check to delete line
            checkToDeleteLine();

        } else {
            currentMino.update();
        }

    }

    private void checkToDeleteLine() {
        int x = left_x;
        int y = top_y;
        int blocksInLineCount = 0;
        int linesCount = 0;

        while (x < right_x && y < bottom_y) {

            for (Block block : staticBlocks) {
                if (block.x == x && block.y == y) {
                    blocksInLineCount++;
                }
            }

            x += Block.SIZE;
            if (x == right_x) {

                // if line is full, we can delete it
                if (blocksInLineCount == 12) {

                    // remove the line
                    int finalY = y;
                    staticBlocks.removeIf(b -> b.y == finalY);

                    // update statistic
                    linesCount++;
                    lines++;

                    // increment drop speed
                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    // shift down blocks
                    for (Block block : staticBlocks) {
                        if (block.y < y) {
                            block.y += Block.SIZE;
                        }
                    }
                }

                y += Block.SIZE;
                x = left_x;
                blocksInLineCount = 0;
            }
        }

        // update score
        if (linesCount > 0) {
            GamePanel.sfx.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * linesCount;
        }
    }

    public void draw(Graphics2D g2) {

        // play area frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        // next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Roboto", Font.BOLD, 30));
        g2.drawString("next", x+60, y+40);

        // score frame
        g2.drawRect(x, top_y, 250, 300);
        g2.drawString("LEVEL: " + level, x+40, top_y+90);
        g2.drawString("LINES: " + lines, x+40, top_y+160);
        g2.drawString("SCORE: " + score, x+40, top_y+230);

        // draw mino
        if(currentMino != null) {
            currentMino.draw(g2);
        }

        // draw next mino
        nextMino.draw(g2);

        // draw staticBlocks
        for (Block block : staticBlocks) {
            block.draw(g2);
        }

        // draw pause
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandler.pause) {
            x = left_x + 80;
            y = top_y + 320;
            g2.drawString("paused", x, y);
        }
        if (gameOver) {

            x = left_x + 30;
            y = top_y + 320;
            g2.drawRect(x - 10, y - 60, 320, 100);
            g2.setColor(Color.BLACK);
            g2.fillRect(x - 5, y - 55, 310, 90);
            g2.setColor(Color.YELLOW);
            g2.drawString("GAME OVER", x, y);
        }

    }
}
