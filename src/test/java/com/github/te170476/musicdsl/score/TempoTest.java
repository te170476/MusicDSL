package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.score.note.NoteValue;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TempoTest {

    @Test
    void toSecParNoteValue() {
        var whole = NoteValue.get(1);
        var half = NoteValue.get(2);
        var forth = NoteValue.get(4);
        var eighth = NoteValue.get(8);
        {
            var tempos =
                Map.of(
                        whole, 30,
                        half, 60,
                        forth, 120,
                        eighth, 240
                ).entrySet().stream()
                    .map(it-> new Tempo(it.getKey(), it.getValue()))
                    .collect(Collectors.toList());
            var head = tempos.get(0);
            tempos.stream().skip(1)
                    .forEach(it-> assertEquals(
                            head.toSecParNoteValue(),
                            it.toSecParNoteValue()
                    ));
        }

        {
            var tempos =
                    Map.of(
                            whole, 15,
                            half, 30,
                            forth, 60,
                            eighth, 120
                    ).entrySet().stream()
                            .map(it-> new Tempo(it.getKey(), it.getValue()))
                            .collect(Collectors.toList());
            var head = tempos.get(0);
            tempos.stream().skip(1)
                    .forEach(it-> assertEquals(
                            head.toSecParNoteValue(),
                            it.toSecParNoteValue()
                    ));
        }
    }

    void equalsTest() {

    }

}
