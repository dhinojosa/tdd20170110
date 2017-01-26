package com.xyzcorp.tdd;

import java.util.Objects;
import java.util.Random;

public class DieImpl implements Die {

    private final Random random;
    private final int pips;

    public DieImpl(Random random) {
        this(random, 1);
    }

    public DieImpl(Random random, int pips) {
        Objects.requireNonNull(random, "Random cannot be null");
       	this.random = random;
        this.pips = pips;
    }

    @Override
    public int getPips() {
        return pips;
    }

    @Override
    public Die roll() {
        return new DieImpl(random, random.nextInt(6) + 1);
    }

	public static Die create() {
		return new DieImpl(new Random());
	}
}
