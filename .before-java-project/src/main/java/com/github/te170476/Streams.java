package com.github.te170476;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static <A, B, Result> Stream<Result> zip(List<A> listA, List<B> listB, BiFunction<A, B, Result> fun) {
        return IntStream.range(0, Math.max(listA.size(), listB.size()))
                .mapToObj(index-> {
                    var a = listA.get(index % listA.size());
                    var b = listB.get(index % listB.size());
                    return fun.apply(a, b);
                });
    }

    public static <A, Reduce, Result> Stream<Result> reducingMap(
            Stream<A> stream,
            Reduce reduceInit,
            BiFunction<A, Reduce, Reduce> reduceFunc,
            BiFunction<A, Reduce, Result> mapFunc
    ) {
        var list = stream.collect(Collectors.toList());
        var reduces = new ArrayList<Reduce>();
        var reducing = reduceInit;
        for (A it : list) {
            reduces.add(reducing);
            reducing = reduceFunc.apply(it, reducing);
        }
        return Streams.zip(list, reduces, mapFunc);
    }
}
