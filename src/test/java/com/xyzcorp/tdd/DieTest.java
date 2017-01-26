package com.xyzcorp.tdd;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DieTest {

	@Test
	public void testDefaultIs1() throws Exception {
		Random random = mock(Random.class);
		Die die = new DieImpl(random);
		assertThat(die.getPips()).isEqualTo(1);
	}

	@Test
	public void testSimpleRollOf4() {
		// Stub
		Random random = new Random() {
			@Override
			public int nextInt(int bound) {
				return 3;
			}
		};

		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isEqualTo(4);
	}

	@Test
	public void testSimpleRollOf5() {
		// Stub
		// Test Double
		// Collaborator
		Random random = new Random() {
			@Override
			public int nextInt(int bound) {
				return 4;
			}
		};

		// Subject Under Test
		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isEqualTo(5);
	}

	@Test
	public void testSimpleRollOf5Then1Mockito() {
		// Mock
		// Test Double
		// Collaborator
		Random random = mock(Random.class);

		// Rehearsal
		when(random.nextInt(5)).thenReturn(4, 0);

		// Subject Under Test
		Die die = new DieImpl(random);
		Die rolledDie = die.roll().roll();
		assertThat(rolledDie.getPips()).isEqualTo(1);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	@Category(value = UnitTest.class)
	public void testThatRandomIsNotNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Random cannot be null");
		new DieImpl(null);
	}

	@Test
	@Category(value = IntegrationTest.class)
	public void testIntegrationWithARealRandom() {
		Die die = new DieImpl(new Random());
		for (int i = 0; i < 1000000; i++) {
			assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);
		}
	}

	@Test
	@Category(value = UnitTest.class)
	public void testBUG123UsingMockito() {
		Random random = mock(Random.class);
		when(random.nextInt(5)).thenReturn(3);
		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isGreaterThan(0).isLessThan(7);
	}

	@Test
	@Category(value = UnitTest.class)
	public void testBUG123UsingEasyMock() {
		Random random = createMock(Random.class);
		expect(random.nextInt(6)).andReturn(5);

		replay(random);

		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isGreaterThan(0).isLessThan(7);

		verify(random);
	}

	@Test
	@Category(value = UnitTest.class)
	public void testBUG123ForZeroUsingEasyMock() {
		Random random = createMock(Random.class);
		expect(random.nextInt(6)).andReturn(0);

		replay(random);

		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isGreaterThan(0).isLessThan(7);

		verify(random);
	}

	@Test
	@Category(value = IntegrationTest.class)
	public void testThatAllValuesAreRolled() {
		int[] values = new int[6];
		Die die = new DieImpl(new Random());
		for (int i = 0; i < 1000000; i++) {
		      Die rolledDie = die.roll();
		      values[rolledDie.getPips() - 1] = values[rolledDie.getPips() - 1] + 1;
		}

		for (int i = 0; i < values.length; i++) {
			assertThat(values[i]).describedAs("%d has been rolled %d times", i + 1, values[i]).isGreaterThan(0);
		}
	}

	@Test
	@Category(value = UnitTest.class)
	public void testBUG403UsingEasyMock() {
		Random random = createMock(Random.class);
		expect(random.nextInt(6)).andReturn(5);

		replay(random);

		Die die = new DieImpl(random);
		Die rolledDie = die.roll();
		assertThat(rolledDie.getPips()).isEqualTo(6);

		verify(random);
	}

	@Test
	public void testFactoryPatternToGiveAStandardDie() {
		Die die = DieImpl.create();
		assertThat(die).isNotNull();
	}
}







