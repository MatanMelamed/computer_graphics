// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Sound;

import javax.sound.sampled.*;
import java.io.*;

public class SoundPlayer {


    private boolean isLoop;
    private Clip clip;

    SoundPlayer(String soundFile) {
        this(soundFile, false);
    }

    SoundPlayer(String soundFilePath, boolean isLoop) {
        this.isLoop = isLoop;
        init(soundFilePath);
    }

    private void init(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundFilePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void Play() {
        if (isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.setMicrosecondPosition(0);
            clip.setFramePosition(0);
            clip.start();
        }
    }

    void Stop() {
        clip.stop();
    }
}
