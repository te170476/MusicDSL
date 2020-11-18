package com.github.te170476.musicdsl;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.util.Collection;
import java.util.Optional;

public class Player {
    AudioFormat format;
    public Player(AudioFormat format) {
        this.format = format;
    }

    public Optional<Clip> play(Collection<Sound> sounds){
        byte[] wave = new byte[]{};
        for (Sound sound : sounds){
            wave = Converter.merge(wave, sound);
        }
        return play(wave);
    }
    public Optional<Clip> play(byte[] wave) {
        try {
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(format, wave, 0, wave.length);
            clip.start();
            return Optional.of(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public void playAndWait(Collection<Sound> sounds) {
        play(sounds).ifPresent(this::waitClip);
    }
    public void playAndWait(byte[] wave) {
        play(wave).ifPresent(this::waitClip);
    }
    private void waitClip(Clip clip) {
        try {
            Thread.sleep(100);
            while (clip.isRunning()){}
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
