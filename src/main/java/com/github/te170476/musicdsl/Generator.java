package com.github.te170476.musicdsl;

public class Generator {
    int amplitude = 127;
    int sampleRate;
    public Generator(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public byte[] sin(double hertz, double second){
        int length = (int)(sampleRate * second);
        byte[] wave = new byte[length];
        double byteParAmplitude = sampleRate / hertz;
        for(int index = 0; index < wave.length; index++){
            wave[index] = (byte) (amplitude * Math.sin((index / byteParAmplitude) * Math.PI * 2.0));
        }
        return wave;
    }
}