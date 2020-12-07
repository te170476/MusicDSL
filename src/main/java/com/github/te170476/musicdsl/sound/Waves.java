package com.github.te170476.musicdsl.sound;

import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;

public class Waves {
    public static final IWaveGenerator sin =
            parameters -> {
                var frequency = parameters.getFrequency();
                return Math.sin(frequency * Math.PI * 2);
            };
    public static final IWaveGenerator square =
            parameters -> {
                int count = (int) (parameters.getFrequency() * 2);
                return count % 2 == 0 ? 1.0 : -1.0;
            };
    public static final IWaveGenerator saw =
            parameters ->
                    ((parameters.getFrequency() + 1) % 2.0 - 1);
    public static final IWaveGenerator triangle =
            parameters -> {
                int count = (int) (parameters.getFrequency() * 4) + 1;
                var sawResult = ((parameters.getFrequency() * 4 + 1) % 2.0 - 1);
                return count % 4 >= 2 ? -sawResult : sawResult;
            };

    public static double getHertz(int pitch) {
        double pitchReference = 440.0;
        return pitchReference * Math.pow(2, pitch / 12.0);
    }
}
