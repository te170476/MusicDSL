package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Generator;
import com.github.te170476.musicdsl.Sound;

public class Note{
    public int pitch;
    public int rate;
    public Note(int pitch, int rate){
        this.pitch = pitch;
        this.rate = rate;
    }
    public Sound toSound(Generator generator, int bpm, int offset) {
        var quarterNoteSec = 60.0 / bpm;
        var noteRate = 4.0 / rate;
        var noteSec = quarterNoteSec * noteRate;
        var wave = generator.saw(generator.getHertz(pitch), noteSec);
        return new Sound(wave, offset);
    }
}
