package com.github.te170476.musicdsl;

import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int sampleRate = 44100;
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        var player = new Player(format);
        var generator = new WaveGenerator(sampleRate);
        List<Sound> sounds = Arrays.asList(
                new Sound(generator.generate(Waves.getHertz(12), sampleRate * 4, Waves.sin), 0),
                new Sound(generator.generate(Waves.getHertz(0), sampleRate * 4, Waves.saw), sampleRate),
                new Sound(generator.generate(Waves.getHertz(-12), sampleRate * 4, Waves.square), sampleRate * 2),
                new Sound(generator.generate(Waves.getHertz(-24), sampleRate * 4, Waves.triangle), sampleRate * 3)
        );
        player.playAndWait(sounds);
    }
}
