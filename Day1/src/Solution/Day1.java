package Solution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Solution.Day1.AbsoluteDirection.*;
import static Solution.Day1.RelativeDirection.*;

/**
 * --- Day 1: No Time for a Taxicab ---
 * 
 * Santa's sleigh uses a very high-precision clock to guide its movements, and
 * the clock's oscillator is regulated by stars. Unfortunately, the stars have
 * been stolen... by the Easter Bunny. To save Christmas, Santa needs you to
 * retrieve all fifty stars by December 25th.
 * 
 * Collect stars by solving puzzles. Two puzzles will be made available on each
 * day in the advent calendar; the second puzzle is unlocked when you complete
 * the first. Each puzzle grants one star. Good luck!
 * 
 * You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near",
 * unfortunately, is as close as you can get - the instructions on the Easter
 * Bunny Recruiting Document the Elves intercepted start here, and nobody had
 * time to work them out further.
 * 
 * The Document indicates that you should start at the given coordinates (where
 * you just landed) and face North. Then, follow the provided sequence: either
 * turn left (L) or right (R) 90 degrees, then walk forward the given number of
 * blocks, ending at a new intersection.
 * 
 * There's no time to follow such ridiculous instructions on foot, though, so
 * you take a moment and work out the destination. Given that you can only walk
 * on the street grid of the city, how far is the shortest path to the
 * destination?
 * 
 * For example:
 * 
 * Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks
 * away. R2, R2, R2 leaves you 2 blocks due South of your starting position,
 * which is 2 blocks away. R5, L5, R5, R3 leaves you 12 blocks away. How many
 * blocks away is Easter Bunny HQ?
 */
public class Day1 {

	static boolean debug = false;

	// Sets debug which will print out the directions and positions at each step if true.
	static void setDebug(boolean debug) {
		Day1.debug = debug;
	}

	// Finds and prints the distance for the default input.
//	public static void main(String[] args) {
//		debug = true;
//		System.out.println(findDistance(null));
//	}

	// Finds the distance of the given input or the default input if input is null.
	static int findDistance(String input) {
		// update document if input is not null
		if (input != null)
			Document.setInput(input);

		// initialize position
		Position position = new Position();

		// follow the directions
		for (Direction dir : Document.getDirections()) {
			// turn
			position.turn(dir.getRelativeDirection());
			// step
			position.step(dir.getStepCount());

			// debug
			if (debug)
				System.out.println("Direction - " + dir + " | Position - " + position);
		}
		
		// debug
		if (debug)
			System.out.println("Final Distance: " + position.getDistance());

		// calculate distance
		return position.getDistance();
	}
	
	// Represents relative directions.
	enum RelativeDirection {
		LEFT,
		RIGHT
	}

	// Represents absolute directions.
	enum AbsoluteDirection {
		NORTH(0),
		EAST(1),
		SOUTH(2),
		WEST(3);

		// List of absolute directions in clockwise order.
		static AbsoluteDirection[] clockwiseCompass = {NORTH, EAST, SOUTH, WEST};

		// Index of the absolute direction in the clockwise compass.
		int index;
		
		AbsoluteDirection(int index) {
			this.index = index;
		}

		// Returns the absolute direction after turning the given relative direction.
		// 
		// This works by using the clockwise compass as a reference for right or left. Turing to 
		// the right corresponds add one to the index and vice versa for left. Since the directions
		// on a compass are naturally circular, the modulo of the new index is taken to prevent
		// indexing out of bounds and 4 is added to the position initially to prevent negative
		// indexes.
		AbsoluteDirection turn(RelativeDirection relDir) {
			int newIndex = ((index + 4) + (relDir == RIGHT ? 1 : -1)) % 4;
			return clockwiseCompass[newIndex];
		}
	}

	// A single direction, i.e. a relative direction and a step count.
	static class Direction{
		
		// Relative direction
		RelativeDirection relativeDirection;

		// Step count
		int stepCount;

		Direction(RelativeDirection relativeDirection, int stepCount) {
			this.relativeDirection = relativeDirection;
			this.stepCount = stepCount;
		}

		// Relative direction getter.
		RelativeDirection getRelativeDirection() {
			return relativeDirection;
		}
		
		// Step count getter
		int getStepCount() {
			return stepCount;
		}

		// Pretty printing
		@Override
		public String toString() {
			return relativeDirection.toString() + " " + stepCount;
		}
	}

	// Represents the current location
	static class Position {
		
		// Meridional position, i.e. North-South position
		int meridionalPosition;

		// Zonal position, i.e. East-West position
		int zonalPosition;

		// Currently facing absolute direction
		AbsoluteDirection orientation;

		Position() {
			meridionalPosition = 0;
			zonalPosition = 0;
			orientation = NORTH; // doesn't really matter for distance calculation
		}

		// Updates the orientation
		void turn(RelativeDirection relDir) {
			orientation = orientation.turn(relDir);
		}

		// Updates the coordinates based on the orientation
		void step(int stepCount) {
			switch(orientation) {
				case NORTH:
					meridionalPosition += stepCount;
					break;
				case EAST:
					zonalPosition += stepCount;
					break;
				case SOUTH:
					meridionalPosition -= stepCount;
					break;
				case WEST:
					zonalPosition -= stepCount;
					break;
			}
		}

		// Returns the Manhattan distance from the origin. 
		int getDistance() {
			return Math.abs(meridionalPosition) + Math.abs(zonalPosition);
		}

		@Override
		public String toString() {
			return "X: " + zonalPosition + ", Y: " + meridionalPosition;
		}
	}

	static class Document {

		// Input to my specific puzzle.
		static String INPUT = "R2, L1, R2, R1, R1, L3, R3, L5, L5, L2, L1, R4, R1, R3, L5, L5, R3, L4, L4, R5, R4, R3, L1, L2, R5, R4, L2, R1, R4, R4, L2, L1, L1, R190, R3, L4, R52, R5, R3, L5, R3, R2, R1, L5, L5, L4, R2, L3, R3, L1, L3, R5, L3, L4, R3, R77, R3, L2, R189, R4, R2, L2, R2, L1, R5, R4, R4, R2, L2, L2, L5, L1, R1, R2, L3, L4, L5, R1, L1, L2, L2, R2, L3, R3, L4, L1, L5, L4, L4, R3, R5, L2, R4, R5, R3, L2, L2, L4, L2, R2, L5, L4, R3, R1, L2, R2, R4, L1, L4, L4, L2, R2, L4, L1, L1, R4, L1, L3, L2, L2, L5, R5, R2, R5, L1, L5, R2, R4, R4, L2, R5, L5, R5, R5, L4, R2, R1, R1, R3, L3, L3, L4, L3, L2, L2, L2, R2, L1, L3, R2, R5, R5, L4, R3, L3, L4, R2, L5, R5";

		// Sets the input for testing.
		static void setInput(String input) {
			Document.INPUT = input;
		}

		// Returns the directions from the given input.
		static List<Direction> getDirections() {
			return Arrays.stream(INPUT.split(","))
				.map(str -> str.trim())
				.map(Document::parseDirection)
				.collect(Collectors.toList());
		}

		// Parses a well formated direction string (i.e. "L34").
		static Direction parseDirection(String dir) {
			int stepCount = Integer.parseInt(dir.substring(1));
			char relDir = dir.charAt(0);
			switch (relDir) {
				case 'R' :
					return new Direction(RIGHT, stepCount);
				case 'L' :
					return new Direction(LEFT, stepCount);
				default :
					throw new IllegalArgumentException(
							"Unable to parse " + dir + " into direction.");
			}
		}
	}
}
