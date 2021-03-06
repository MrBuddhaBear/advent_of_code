package Solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static Solution.Day1Part2.AbsoluteDirection.*;
import static Solution.Day1Part2.RelativeDirection.*;

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
 * 
 * --- Part Two ---
 * 
 * Then, you notice the instructions continue on the back of the Recruiting
 * Document. Easter Bunny HQ is actually at the first location you visit twice.
 * 
 * For example, if your instructions are R8, R4, R4, R8, the first location you
 * visit twice is 4 blocks away, due East.
 * 
 * How many blocks away is the first location you visit twice?
 */
public class Day1Part2 {

	static boolean debug = false;

	// Sets debug which will print out the directions and positions at each step if true.
	static void setDebug(boolean debug) {
		Day1Part2.debug = debug;
	}

	// Finds and prints the distance for the default input.
	public static void main(String[] args) {
		debug = true;
		Position position = findDistance(null);
		System.out.println("End distance: " + position.getDistance());
		System.out.println("Revisit distance: " + position.getFirstRevisitDistance());
	}

	// Finds the distance of the given input or the default input if input is null.
	static Position findDistance(String input) {
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

		// return position
		return position;
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
		Integer meridionalPosition;

		// Zonal position, i.e. East-West position
		Integer zonalPosition;

		// Currently facing absolute direction
		AbsoluteDirection orientation;

		// History of visited positions
		History history;

		Position() {
			meridionalPosition = 0;
			zonalPosition = 0;
			history = new History(this);
			orientation = NORTH; // doesn't really matter for distance calculation

			// record current position
			history.record();
		}

		// Updates the orientation
		void turn(RelativeDirection relDir) {
			orientation = orientation.turn(relDir);
		}

		// Updates the coordinates based on the orientation and records the history.
		void step(int stepCount) {
			switch(orientation) {
				case NORTH:
					for (int i = 0; i < stepCount; i++) {
						meridionalPosition += 1;
						history.record();
					}
					break;
				case EAST:
					for (int i = 0; i < stepCount; i++) {
						zonalPosition += 1;
						history.record();
					}
					break;
				case SOUTH:
					for (int i = 0; i < stepCount; i++) {
						meridionalPosition -= 1;
						history.record();
					}
					break;
				case WEST:
					for (int i = 0; i < stepCount; i++) {
						zonalPosition -= 1;
						history.record();
					}
					break;
			}
		}

		// Returns the Manhattan distance from the origin of the current position. 
		int getDistance() {
			return Math.abs(meridionalPosition) + Math.abs(zonalPosition);
		}

		// Returns the Manhattan distance of the first re-visited position.
		int getFirstRevisitDistance() {
			if (history.firstRevisit == null)
				return -1;

			return Math.abs(history.firstRevisit.meridionalPosition)
					+ Math.abs(history.firstRevisit.zonalPosition);
		}

		@Override
		public String toString() {
			return "X: " + zonalPosition + ", Y: " + meridionalPosition;
		}
	}

	// Represents the history of all traveled positions.
	static class History {

		// A lightweight snapshot of a position.
		static class PositionLite {

			// Meridional position, i.e. North-South position
			int meridionalPosition;

			// Zonal position, i.e. East-West position
			int zonalPosition;

			PositionLite(Position position) {
				meridionalPosition = position.meridionalPosition;
				zonalPosition = position.zonalPosition;
			}

			// Override equals to compare positional values for use in hashset.
			@Override
			public boolean equals(Object obj) {
			    if (obj == null) {
			        return false;
			    }
			    if (!PositionLite.class.isAssignableFrom(obj.getClass())) {
			        return false;
				}
				final PositionLite other = (PositionLite) obj;
				return (meridionalPosition == other.meridionalPosition)
						&& (zonalPosition == other.zonalPosition);
			}

			// Override hashcode to compare positional values for use in hashset.
			@Override
			public int hashCode() {
				return ("" + meridionalPosition + zonalPosition).hashCode();
			}
		}
		
		// Set of visited positions.
		HashSet<PositionLite> visited;

		// First re-visited position
		PositionLite firstRevisit;

		// Handle to current position 
		Position position;

		History(Position position) {
			this.position = position;
			visited = new HashSet<PositionLite>();
		};

		// Record the current position.
		void record() {
			// stop recording if path has already crossed
			if (firstRevisit != null)
				return;
			
			PositionLite currentPosition = new PositionLite(position);
			boolean newlyAdded = visited.add(currentPosition);
			if (!newlyAdded)
				firstRevisit = currentPosition;
			
			System.out.println(!newlyAdded + " " + position);
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
