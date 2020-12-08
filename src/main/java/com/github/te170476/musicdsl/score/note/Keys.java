package com.github.te170476.musicdsl.score.note;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Keys {
    public static final int max = 12;
    private static final List<Key> keys =
            IntStream.range(0, max)
                    .mapToObj(Key::new)
                    .collect(Collectors.toUnmodifiableList());
    public static Key get(int index) {
        var surplus = index % max;
        if (surplus < 0)
            return keys.get(max + surplus);
        else
            return keys.get(surplus);
    }
//    public static final Key Cf = get(11);
    public static final Key C = get(0);
    public static final Key Cs = get(1);
    public static final Key Df = get(1);
    public static final Key D = get(2);
    public static final Key Ds = get(3);
    public static final Key Ef = get(3);
    public static final Key E = get(4);
//    public static Key Es = get(5);
//    public static Key Ff = get(4);
    public static final Key F = get(5);
    public static final Key Fs = get(6);
    public static final Key Gf = get(6);
    public static final Key G = get(7);
    public static final Key Gs = get(8);
    public static final Key Af = get(8);
    public static final Key A = get(9);
    public static final Key As = get(10);
    public static final Key Bf = get(10);
    public static final Key B = get(11);
//    public static Key Bs = get(0);
}
