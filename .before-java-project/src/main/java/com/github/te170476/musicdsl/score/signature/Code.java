package com.github.te170476.musicdsl.score.signature;

import com.github.te170476.musicdsl.score.tone.Tone;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Code {
    public Pitch root;
    public List<Pitch> pitches;
    Code(Pitch root, List<Pitch> pitches) {
        this.root = root;
        this.pitches = pitches.stream()
                .map(root::moved)
                .collect(Collectors.toList());
    }

    public List<Tone> toTones() {
        var pitches = Stream.concat(List.of(root).stream(), this.pitches.stream());
        return pitches.map(it-> it.value < root.value ? it.setOctave(1) : it.toTone())
                .collect(Collectors.toList());
    }
}
