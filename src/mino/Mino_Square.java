package mino;

import java.awt.*;

public class Mino_Square extends Mino{

    public Mino_Square() {
        create(Color.YELLOW);
    }

    @Override
    public void setXY(int x, int y) {
        //
        //  o o     b[0]b[1]
        //  o o     b[2]b[3]
        //
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x + Block.SIZE;
        b[1].y = b[0].y;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;

    }

    @Override
    public void setDirection1() {

    }

    @Override
    public void setDirection2() {

    }

    @Override
    public void setDirection3() {

    }

    @Override
    public void setDirection4() {

    }
}
