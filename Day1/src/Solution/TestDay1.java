package Solution;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import static Solution.Day1.AbsoluteDirection.*;
import static Solution.Day1.RelativeDirection.*;

public class TestDay1 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Day1.setDebug(true);
	}

	@Test
	public void testAbsoluteDirection_turnLeft() {
		// test left turns
		assertEquals(WEST, NORTH.turn(LEFT));
		assertEquals(NORTH, EAST.turn(LEFT));
		assertEquals(EAST, SOUTH.turn(LEFT));
		assertEquals(SOUTH, WEST.turn(LEFT));

		// test right turns
		assertEquals(EAST, NORTH.turn(RIGHT));
		assertEquals(SOUTH, EAST.turn(RIGHT));
		assertEquals(WEST, SOUTH.turn(RIGHT));
		assertEquals(NORTH, WEST.turn(RIGHT));
	}

	@Test
	public void testEnd2EndSample1() {
		String input = "R2, L3";
		int expected = 5;

		int actual = Day1.findDistance(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testEnd2EndSample2() {
		String input = "R2, R2, R2";
		int expected = 2;

		int actual = Day1.findDistance(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testEnd2EndSample3() {
		String input = "R5, L5, R5, R3";
		int expected = 12;

		int actual = Day1.findDistance(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testEnd2EndCircles() {
		String input = "R5, R5, R5, R5, L1, L1, L1, L1";
		int expected = 0;

		int actual = Day1.findDistance(input);

		assertEquals(expected, actual);
	}


	@Test
	public void testEnd2EndSpirals() {
		// turn off debugging for this test
		Day1.setDebug(false);

		// number of spirals to test
		// 5000 takes about 3 seconds to run on my MacBook Air
		int maxSpiral = 5000;

		String input;
		int expected;
		for (int i = 1; i < maxSpiral; i ++) {
			input = generateSpiralInput(i);
			expected = generateSpiralExpected(i);

			int actual = Day1.findDistance(input);

			assertEquals(expected, actual);
		}
	}

	public String generateSpiralInput(int num) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i <= num; i++) {
			if (i == 1) {
				buffer.append("R1");
			} else {
				buffer.append(", R" + i);
			}
		}
		return buffer.toString();
	}

	public int generateSpiralExpected(int num) {
		if (num == 1)
			return 1;

		return (num/2) + (num % 2) + 2 * ((num - 2) / 4) + 2;
	}
}
