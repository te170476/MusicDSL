package com.github.te170476.musicdsl;

import javax.sound.sampled.AudioFormat;

public class Sound {
    AudioFormat format;
    byte[] wave;
    int offset;
    public Sound(AudioFormat format, byte[] wave, int offset){
        this.wave = wave;
        this.offset = offset;
    }
}
