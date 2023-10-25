package main;

import mino.*;

import java.awt.*;
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

    public static int dropInterval = 60;

    public PlayManager() {

        left_x = GamePanel.WIDTH/2 - WIDTH/2;
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + WIDTH/2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // starting mino
        currentMino = getMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

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

        currentMino.update();

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

        // draw mino
        if(currentMino != null) {
            currentMino.draw(g2);
        }

    }
}
