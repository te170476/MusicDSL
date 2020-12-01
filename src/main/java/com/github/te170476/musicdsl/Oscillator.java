package com.github.te170476.musicdsl;

public class Oscillator {
    static final int SAMPLE_RATE=44100;
    double freq,dt;
    int time;

    public Oscillator(int key){
        dt = Math.PI*2/SAMPLE_RATE;
        freq = 440*(Math.pow(2,((key-69)/12)));
        time = 0;
    }

    public double read(){
        double w = (Math.sin(dt*time*freq)*127);
        time++;
        return w;
    }
}
