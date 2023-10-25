package mino;

import java.awt.*;

import main.KeyHandler;
import main.PlayManager;

public abstract class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;

    public void create(Color c) {

        for (int i = 0; i < b.length; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }

    }

    public abstract void setXY(int x, int y);

    public void updateXY(int direction) {

        this.direction = direction;

        System.arraycopy(tempB, 0, b, 0, b.length);

    }

    public abstract void setDirection1();
    public abstract void setDirection2();
    public abstract void setDirection3();
    public abstract void setDirection4();

    public void update() {

        // move controls
        if (KeyHandler.upPressed) {

            switch (direction) {
                case 1 :
                    setDirection2();
                    break;
                case 2 :
                    setDirection3();
                    break;
                case 3 :
                    setDirection4();
                    break;
                case 4 :
                    setDirection1();
                    break;
            }

            KeyHandler.upPressed = false;

        }
        if (KeyHandler.downPressed) {

            for (Block block : b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;

            KeyHandler.downPressed = false;

        }
        if (KeyHandler.leftPressed) {

            for (Block block : b) {
                block.x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;

        }
        if (KeyHandler.rightPressed) {

            for (Block block : b) {
                block.x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

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
