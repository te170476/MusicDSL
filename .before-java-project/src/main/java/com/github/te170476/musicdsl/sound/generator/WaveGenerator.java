package com.github.te170476.musicdsl.sound.generator;

public class WaveGenerator {
    int amplitude = 127;
    public int sampleRate;
    public WaveGenerator(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public byte[] generate(double hertz, int length, IWaveGenerator waveGen) {
        return generate(hertz, length, 0, waveGen, 110);
    }
    public byte[] generate(double hertz, int length, IWaveGenerator waveGen, int strength) {
        return generate(hertz, length, 0, waveGen, strength);
    }
    public byte[] generate(double hertz, int length, int offset, IWaveGenerator waveGen, int strength) {
        var dbModoki = 50 *  Math.log10((double)strength / 127);
        System.out.println(amplitude + dbModoki);
        byte[] wave = new byte[length];
        for(int index = 0; index < wave.length; index++){
            var parameters = new Parameters(sampleRate, index + offset, hertz);
            wave[index] = (byte) ((amplitude + dbModoki) * waveGen.apply(parameters));
        }
        return wave;
    }
}
