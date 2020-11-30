package com.github.te170476.musicdsl.score.note;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pitches {
    private static final int max = 12;
    private static final List<Pitch> pitches =
            IntStream.range(0, max)
                    .mapToObj(Pitch::new)
                    .collect(Collectors.toUnmodifiableList());
    public static Pitch get(int index) {
        return pitches.get(index % max);
    }
//    public static final Pitch De = get(11);
    public static final Pitch Do = get(0);
    public static final Pitch Di = get(1);
    public static final Pitch Ra = get(1);
    public static final Pitch Re = get(2);
    public static final Pitch Ri = get(3);
    public static final Pitch Me = get(3);
    public static final Pitch Mi = get(4);
//    public static final Pitch Ma = get(5);
//    public static final Pitch Fe = get(4);
    public static final Pitch Fa = get(5);
    public static final Pitch Fi = get(6);
    public static final Pitch Se = get(6);
    public static final Pitch So = get(7);
    public static final Pitch Si = get(8);
    public static final Pitch Le = get(8);
    public static final Pitch La = get(9);
    public static final Pitch Li = get(10);
    public static final Pitch Te = get(10);
    public static final Pitch Ti = get(11);
//    public static final Pitch To = get(0);

    public static final Pitch major2 = get(4);
    public static final Pitch major3 = get(7);
    public static final Pitch minor2 = get(3);
    public static final Pitch minor3 = get(7);
    public static final Pitch augmented2 = get(4);
    public static final Pitch augmented3 = get(8);
    public static final Pitch diminished2 = get(3);
    public static final Pitch diminished3 = get(6);

    public static final Pitch fifth = get(7);
    public static final Pitch sixth = get(9);
    public static final Pitch minorSeventh = get(10);
    public static final Pitch seventh = get(11);

    public static final List<Pitch> major = Collections.unmodifiableList(List.of(major2, major3));
    public static final List<Pitch> minor = Collections.unmodifiableList(List.of(minor2, minor3));
    public static final List<Pitch> augmented = Collections.unmodifiableList(List.of(augmented2, augmented3));
    public static final List<Pitch> diminished = Collections.unmodifiableList(List.of(diminished2, diminished3));
}
