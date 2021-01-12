package com.github.te170476.musicdsl.score.note;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NoteValue {
    public static INoteValue get(int... index) {
        return new Values(
                Arrays.stream(index)
                        .mapToObj(Value::get)
                        .collect(Collectors.toList())
        );
    }
    public static INoteValue get(INoteValue... index) {
        return new Values(
                Arrays.stream(index)
                        .collect(Collectors.toList())
        );
    }

    public interface LeafNode extends INoteValue {}
    public interface InnerNode extends INoteValue {}

    static class Value implements LeafNode {
        public final int value;

        public static Value get(int index) {
            return new Value(index);
        }
        private Value(int noteValue) {
            this.value = noteValue;
        }

        @Override
        public double toPercentage() {
            return value == 0 ? 0.0 : 1.0 / value;
        }
    }
    
    static class Values implements InnerNode {
        public List<INoteValue> leaves;
        public Values(List<INoteValue> leaves) {
            this.leaves = leaves;
        }

        @Override
        public double toPercentage() {
            return leaves.stream()
                    .mapToDouble(INoteValue::toPercentage)
                    .sum();
        }
    }
    static class HasBase implements InnerNode {
        public INoteValue base;
        public INoteValue value;
        public HasBase(INoteValue base, INoteValue value) {
            this.base = base;
            this.value = value;
        }

        @Override
        public double toPercentage() {
            return value.toPercentage() * base.toPercentage();
        }
    }
    static class Negative implements InnerNode {
        public INoteValue value;
        public Negative(INoteValue value) {
            this.value = value;
        }
        @Override
        public double toPercentage() {
            return value.toPercentage() * -1;
        }
    }
}
