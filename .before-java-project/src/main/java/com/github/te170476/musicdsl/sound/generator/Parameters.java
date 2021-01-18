package com.github.te170476.musicdsl.sound.generator;

public class Parameters {
    public final int sampleRate;
    public final int timeIndex;
    public final double hertz;
    public Parameters(int sampleRate, int timeIndex, double hertz){
        this.sampleRate = sampleRate;
        this.timeIndex = timeIndex;
        this.hertz = hertz;
    }

    public double getFrequency() {
        return timeIndex * hertz / sampleRate;
    }
}
