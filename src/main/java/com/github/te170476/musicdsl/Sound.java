package com.github.te170476.musicdsl;

public class Sound {
    byte[] wave;
    int offset;
    public Sound(byte[] wave, int offset){
        this.wave = wave;
        this.offset = offset;
    }
}
