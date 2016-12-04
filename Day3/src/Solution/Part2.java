package Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {

	public static void main(String[] args) throws IOException {
		String input = "input.txt";
		String sample = "samplePart2.txt";

		int inputCount = count(input);
		int sampleCount = count(sample);

		System.out.println("Input: " + inputCount);
		System.out.println("Sample: " + sampleCount);
	}

	static int count(String filename) throws NumberFormatException, IOException {
		int sum = 0;
		List<List<Integer>> numListList = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
				.map(str -> str.trim().split("\\s+")).map(strArr -> Arrays.stream(strArr)
						.map(num -> Integer.parseInt(num.trim())).collect(Collectors.toList()))
				.collect(Collectors.toList());
		for (int i = 0; i < numListList.size(); i += 3) {
			sum += countTriangles(numListList.get(i), numListList.get(i + 1), numListList.get(i + 2));
		}
		return sum;
	}

	static int countTriangles(List<Integer> row1, List<Integer> row2, List<Integer> row3) {
		return isTriangle(row1.get(0), row2.get(0), row3.get(0)) + isTriangle(row1.get(1), row2.get(1), row3.get(1))
				+ isTriangle(row1.get(2), row2.get(2), row3.get(2));
	}

	static int isTriangle(int side1, int side2, int side3) {
		return (side1 + side2 <= side3 || side2 + side3 <= side1 || side3 + side1 <= side2) ? 0 : 1;
	}
}