package com.github.te170476.musicdsl;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int sampleRate = 44100;
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        var player = new Player(format);
        var generator = new Generator(sampleRate);
        List<Sound> sounds = Arrays.asList(
                new Sound(generator.sin(generator.getHertz(12), 1), 0),
                new Sound(generator.saw(generator.getHertz(0), 1), 0),
                new Sound(generator.square(generator.getHertz(-12), 1), 0)
        );
        player.playAndWait(sounds);
    }
}
