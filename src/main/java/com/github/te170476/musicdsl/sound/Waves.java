package com.github.te170476.musicdsl.sound;

import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;

public class Waves {
    public static final IWaveGenerator sin =
            attributes -> {
                var frequency = attributes.getFrequency();
                return Math.sin(frequency * Math.PI * 2);
            };
    public static final IWaveGenerator saw =
            attributes ->
                (attributes.getFrequency() % 1.0 - 0.5) * 2;
    public static final IWaveGenerator square =
            attributes -> {
                int count = (int) (attributes.getFrequency() * 2);
                return count % 2 == 0 ? 1.0 : 0.0;
            };
    public static final IWaveGenerator triangle =
            attributes -> {
                int count = (int) (attributes.getFrequency() * 2);
                var sawResult = saw.apply(attributes);
                return count % 2 == 0 ? sawResult : 1 - sawResult;
            };

    public static double getHertz(int pitch) {
        double pitchReference = 440.0;
        return pitchReference * Math.pow(2, pitch / 12.0);
    }
}
