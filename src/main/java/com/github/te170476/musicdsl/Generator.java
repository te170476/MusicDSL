package com.github.te170476.musicdsl;

public class Generator {
    int sampleRate;
    public Generator(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    public byte[] sin(double hertz, double second){
        int length = (int)(sampleRate * second);
        byte[] wave = new byte[length];
        double pitch = sampleRate / hertz;
        for(int index = 0; index < wave.length; index++){
            wave[index] = (byte) (110 * Math.sin((index/pitch) * Math.PI * 2));
        }
        return wave;
    }
}
