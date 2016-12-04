package Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

	public static void main(String[] args) throws IOException {
		String input = "input.txt";
		String sample = "samplePart1.txt";
		
		int inputCount = count(input);
		int sampleCount = count(sample);

		System.out.println("Input: " + inputCount);
		System.out.println("Sample: " + sampleCount);
	}

	static int count(String filename) throws NumberFormatException, IOException {
		return Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
				.map(str -> str.trim().split("\\s+")).map(strArr -> Arrays.stream(strArr)
						.map(num -> Integer.parseInt(num.trim())).collect(Collectors.toList()))
				.mapToInt(Part1::isTriangle).sum();
	}

	static int isTriangle(List<Integer> numList) {
		int side1 = numList.get(0);
		int side2 = numList.get(1);
		int side3 = numList.get(2);
		if (side1 + side2 <= side3
		 || side2 + side3 <= side1
		 || side3 + side1 <= side2)
			return 0;
		return 1;
	}
}