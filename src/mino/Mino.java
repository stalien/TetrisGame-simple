package mino;

import java.awt.*;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

public abstract class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision;
    boolean rightCollision;
    boolean bottomCollision;
    boolean rotateCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {

        for (int i = 0; i < b.length; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }

    }

    public abstract void setXY(int x, int y);

    public void updateXY(int direction) {

        checkRotationCollision();

        if (!rotateCollision) {
            this.direction = direction;
            System.arraycopy(tempB, 0, b, 0, b.length);
        }

    }

    public abstract void setDirection1();
    public abstract void setDirection2();
    public abstract void setDirection3();
    public abstract void setDirection4();
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // calculate collisions
        for (Block block : b) {
            // left wall
            if (block.x == PlayManager.left_x) {
                leftCollision = true;
            }
            // right wall
            if (block.x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }
            // floor
            if (block.y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }

    }
    public void checkRotationCollision() {
        rotateCollision = false;

        checkStaticBlockCollision(tempB);

        for (Block block : tempB) {
            if (block.x < PlayManager.left_x) {
                rotateCollision = true;
                break;
            }
            if (block.x + Block.SIZE > PlayManager.right_x) {
                rotateCollision = true;
                break;
            }
            if (block.y + Block.SIZE > PlayManager.bottom_y) {
                rotateCollision = true;
                break;
            }
        }

    }

    public void checkStaticBlockCollision(Block[] blocks) {
        for (Block staticBlock : PlayManager.staticBlocks) {
            int targetX = staticBlock.x;
            int targetY = staticBlock.y;

            for (Block minoBlock : b) {
                if (minoBlock.y + Block.SIZE == targetY && minoBlock.x == targetX) {
                    bottomCollision = true;
                    rotateCollision = true;
                }
                if (minoBlock.x == targetX + Block.SIZE && minoBlock.y == targetY) {
                    leftCollision = true;
                    rotateCollision = true;
                }
                if (minoBlock.x + Block.SIZE == targetX && minoBlock.y == targetY) {
                    rightCollision = true;
                    rotateCollision = true;
                }
            }
        }
    }

    public void update() {

        if (deactivating) {
            deactivate();
        }

        checkMovementCollision();
        checkStaticBlockCollision(b);

        // move controls
        if (KeyHandler.upPressed) {

            switch (direction) {
                case 1:
                    setDirection2();
                    break;
                case 2:
                    setDirection3();
                    break;
                case 3:
                    setDirection4();
                    break;
                case 4:
                    setDirection1();
                    break;
            }
            GamePanel.sfx.play(3, false);
            KeyHandler.upPressed = false;

        }
        if (KeyHandler.downPressed) {

            if (!bottomCollision) {
                for (Block block : b) {
                    block.y += Block.SIZE;
                }
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;

        }
        if (KeyHandler.leftPressed) {

            if (!leftCollision) {
                for (Block block : b) {
                    block.x -= Block.SIZE;
                }
            }
            KeyHandler.leftPressed = false;

        }
        if (KeyHandler.rightPressed) {

            if (!rightCollision) {
                for (Block block : b) {
                    block.x += Block.SIZE;
                }
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
            if (!deactivating) {
                GamePanel.sfx.play(4, false);
            }
            deactivating = true;
        } else {
            autoDropCounter++; // increased every frame
            if (autoDropCounter == PlayManager.dropInterval) {
                // mino go down
                for (Block block : b) {
                    block.y += Block.SIZE;
                }
                autoDropCounter = 0;
            }
        }

    }

    private void deactivate() {
        deactivateCounter++;

        if (deactivateCounter > 44) {
            checkMovementCollision();
            checkStaticBlockCollision(b);
            if (bottomCollision) {
                active = false;
            }

            deactivateCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(b[0].c);
        for (Block block : b) {
            g2.fillRect(block.x, block.y, Block.SIZE, Block.SIZE);
        }

    }

}
