package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pause;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == VK_UP) {
            upPressed = true;
        }
        if (code == VK_DOWN) {
            downPressed = true;
        }
        if (code == VK_LEFT) {
            leftPressed = true;
        }
        if (code == VK_RIGHT) {
            rightPressed = true;
        }
        if (code == VK_SPACE) {
            pause = !pause;
            if (pause) {
                GamePanel.music.stop();
            } else {
                GamePanel.music.play(0, true);
                GamePanel.music.loop();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
