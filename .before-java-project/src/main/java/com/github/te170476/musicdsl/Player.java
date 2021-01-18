package com.github.te170476.musicdsl;

import com.github.te170476.musicdsl.sound.Converter;

import javax.sound.sampled.*;
import java.util.Collection;
import java.util.Optional;

public class Player {
    public SourceDataLine dataLine;

    public static Optional<Player> open(AudioFormat format) {
        return open(format, 512);
    }
    public static Optional<Player> open(AudioFormat format, int bufferSize) {
        return createSourceDataLine(format)
                .filter(it-> open(it, format, bufferSize))
                .map(Player::new);
    }
    private static Optional<SourceDataLine> createSourceDataLine(AudioFormat format) {
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!(AudioSystem.isLineSupported(info)))
                return Optional.empty();
            return Optional.of((SourceDataLine) AudioSystem.getLine(info));
        } catch (LineUnavailableException ignored) {}
        return Optional.empty();
    }
    private static boolean open(SourceDataLine dataLine, AudioFormat format, int bufferSize) {
        try {
            dataLine.open(format, bufferSize);
        } catch (LineUnavailableException e) {
            return false;
        }
        return true;
    }
    private Player(SourceDataLine dataLine) {
        this.dataLine = dataLine;
        dataLine.start();
    }

    public void play(Collection<Sound> sounds){
        byte[] wave = new byte[]{};
        for (Sound sound : sounds){
            wave = Converter.merge(wave, sound);
        }
        play(wave);
    }
    public void play(byte[] wave) {
        dataLine.write(wave, 0, wave.length);
    }
    public void playAndWait(Collection<Sound> sounds) {
        play(sounds);
        waitClip(dataLine);
    }
    public void playAndWait(byte[] wave) {
        play(wave);
        waitClip(dataLine);
    }
    private void waitClip(SourceDataLine dataLine) {
        dataLine.drain();
    }
}
