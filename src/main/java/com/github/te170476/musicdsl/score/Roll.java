package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.signature.AbsoluteTone;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class Roll {
    static class Note {
        public final Line.Note note;
        public final INoteValue offset;
        public Note(Line.Note note, INoteValue offset){
            this.note = note;
            this.offset = offset;
        }

        public Sound toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen) {
            var offset = NoteValue.get(initOffset, this.offset);
            return note.toSound(root, generator, tempo, offset, waveGen);
        }
    }

    public final List<Note> notes;
    public Roll(List<Note> notes) {
        this.notes = notes;
    }

    public List<Sound> toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen) {
        return notes.stream()
                .map(it-> it.toSound(root, generator, tempo, initOffset, waveGen))
                .collect(Collectors.toList());
    }
}
