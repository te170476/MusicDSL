package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.ArrayList;
import java.util.List;

public class Line {
    static class Note {
        public int pitch;
        public INoteValue noteValue;
        public Note(int pitch, INoteValue noteValue){
            this.pitch = pitch;
            this.noteValue = noteValue;
        }
        public Sound toSound(WaveGenerator generator, Tempo tempo, INoteValue offset, IWaveGenerator waveGen) {
            var offsetSec = tempo.toSecParNoteValue() * offset.toPercentage();
            var offsetLength = (int)(offsetSec * generator.sampleRate);
            var noteSec = tempo.toSecParNoteValue() * noteValue.toPercentage();
            var noteLength = (int) (noteSec * generator.sampleRate);
            var wave = generator.generate(Waves.getHertz(pitch), noteLength, waveGen);
            return new Sound(wave, offsetLength);
        }
    }

    public final List<Note> notes;
    public Line(List<Note> notes) {
        this.notes = notes;
    }

    public Roll toRoll(INoteValue initOffset) {
        List<Roll.Note> rollNotes = new ArrayList<>();
        INoteValue offset = initOffset;
        for (Note note : notes) {
            rollNotes.add(new Roll.Note(note, offset));
            offset = NoteValue.get(offset, note.noteValue);
        }
        return new Roll(rollNotes);
    }
}