package com.xyzcorp.tdd;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalcStats {

    private final int[] array;

    public CalcStats(int... array) {
        if (array == null)
            throw new IllegalArgumentException("Array is null");
        this.array = array;
    }

    private Optional<Integer> filter(Comparator<Integer> comparator) {
        return Arrays.stream(array).boxed().sorted(comparator)
                .findFirst();
    }

//	private Optional<Integer> filter2(BiPredicate<Integer, Integer> biPredicate) {
//		if (array.length == 0)
//			return Optional.empty();
//		int result = array[0];
//		for (int i = 1; i < array.length; i++) {
//			if (biPredicate.test(array[i], result)) {
//				result = array[i];
//			}
//		}
//		return Optional.of(result);
//	}

    public Optional<Integer> getMinimum() {
        return filter(Comparator.naturalOrder());
    }

    public Optional<Integer> getMaximum() {
        return filter(Comparator.reverseOrder());
    }

    public int getSize() {
        return array.length;
    }

    public Optional<Double> getAverage() {
        if (array.length == 0) return Optional.empty();
        Double collect = IntStream.of(array).asDoubleStream().boxed()
                .collect(Collectors.averagingDouble(value -> value));
        return Optional.of(collect);
    }
}
