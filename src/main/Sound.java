package main;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip musicClip;
    URL[] urls = new URL[5];

    public Sound() {
        urls[0] = getClass().getResource("/game_music.wav");
        urls[1] = getClass().getResource("/delete line.wav");
        urls[2] = getClass().getResource("/gameover.wav");
        urls[3] = getClass().getResource("/rotation.wav");
        urls[4] = getClass().getResource("/touch floor.wav");
    }

    public void play(int i, boolean music) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(urls[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();

        } catch (Exception e) {

        }

    }

    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        musicClip.stop();
        musicClip.close();
    }

}
