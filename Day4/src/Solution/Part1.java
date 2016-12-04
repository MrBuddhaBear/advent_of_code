package Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Part1 {

	public static void main(String[] args) throws IOException {
		String input = "input.txt";

		int sum = Files.lines(Paths.get(input), StandardCharsets.UTF_8).map(str -> new Code(str))
				.mapToInt(code -> code.isReal ? code.id : 0).sum();

		System.out.println("Sum: " + sum);
	}

	static class Code {
		String[] words;
		int id;
		String checkSum;

		boolean isReal;

		Code(String encoded) {
			String[] tokens = encoded.trim().split("-");
			words = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
			String[] idAndCheckSum = tokens[tokens.length - 1]
					.substring(0, tokens[tokens.length - 1].length() - 1).split("\\[");
			id = Integer.parseInt(idAndCheckSum[0]);
			checkSum = idAndCheckSum[1];
			analyze();
		}

		void analyze() {
			HashMap<Character, Integer> charFreq = new HashMap<>();
			Arrays.stream(words).forEach(str -> str.chars().mapToObj(c -> (char) c).forEach(
					c -> charFreq.put(c, charFreq.containsKey(c) ? charFreq.get(c) + 1 : 1)));
			ArrayList<Character> topFive = new ArrayList<>(5);
			charFreq.keySet().stream().forEach(c -> insert(c, topFive, charFreq));
			isReal = checkSum.chars().mapToObj(c -> (char) c).allMatch(c -> topFive.contains(c));
		}

		void insert(Character c, ArrayList<Character> topFive,
				HashMap<Character, Integer> charFreq) {
			boolean added = false;
			for (int i = 0; i < 5 && !added; i++) {
				if (topFive.size() <= i) {
					topFive.add(i, c);
					added = true;
				} else {
					int insertFreq = charFreq.get(c);
					int listFreq = charFreq.get(topFive.get(i));
					if (insertFreq > listFreq
							|| insertFreq == listFreq && c.compareTo(topFive.get(i)) < 0) {
						topFive.add(i, c);
						added = true;
						if (topFive.size() == 6)
							topFive.remove(5);
					}
				}
			}
		}
	}
}
