package com.xyzcorp.tdd;

import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LambdaDieTest  {

    @Test
    public void testSimpleRollOf5Then1Mockito() {
        // Function
        // Test Double
        // Collaborator

        // Subject Under Test
        LambdaDie die = new LambdaDie(() -> 4);
        LambdaDie rolledDie = die.roll().roll();
        assertThat(rolledDie.getPips()).isEqualTo(4);
    }
    
    @Test
    public void testSimpleRollIntegrationWithJavaUtilRandom() {
        // Function
        // Test Double
        // Collaborator
        // Subject Under Test
    	    Random random = new Random();
        LambdaDie die = new LambdaDie(() -> random.nextInt(6) + 1);
        LambdaDie rolledDie = die.roll().roll();
        assertThat(rolledDie.getPips()).isEqualTo(4);
    }
}
