package com.github.te170476.musicdsl;

public class Sound {
    public byte[] wave;
    public int offset;
    public Sound(byte[] wave, int offset){
        this.wave = wave;
        this.offset = offset;
    }
}
