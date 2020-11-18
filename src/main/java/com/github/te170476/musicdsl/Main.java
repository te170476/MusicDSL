package com.github.te170476.musicdsl;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int sampleRate = 44100;
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        var player = new Player(format);
        var generator = new Generator(sampleRate);
        var sound = Arrays.asList(
                new Sound(format, generator.sin(261.626, 2), 0 * sampleRate),
                new Sound(format, generator.sin(329.628, 2), 0 * sampleRate),
                new Sound(format, generator.sin(391.995, 2), 0 * sampleRate)
        );
        player.playAndWait(sound);
    }
}
