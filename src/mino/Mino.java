package mino;

import java.awt.*;
import main.PlayManager;

public class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;

    public void create(Color c) {

        for (int i = 0; i < b.length; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }

    }

    public void setXY(int x, int y) {

    }

    public void updateXY(int direction) {

    }

    public void update() {

        autoDropCounter++; // increased every frame
        if (autoDropCounter == PlayManager.dropInterval) {
            // mino go down
            for (Block block : b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {

        g2.setColor(b[0].c);
        for (Block block : b) {
            g2.fillRect(block.x, block.y, Block.SIZE, Block.SIZE);
        }

    }

}
