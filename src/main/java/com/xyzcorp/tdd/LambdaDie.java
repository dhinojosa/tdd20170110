package com.xyzcorp.tdd;

import java.util.function.Supplier;

public class LambdaDie implements Die {
    public LambdaDie(Supplier<Integer> supplier) {
    }

    public LambdaDie roll() {

      return null;
    }

    public int getPips() {
        return 0;
    }
}
