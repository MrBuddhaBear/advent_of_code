package Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

	static int[][] board = {{1, 2, 3},
					        {4, 5, 6},
					        {7, 8, 9}};

	int row, col;

//	public static void main(String[] args) throws IOException {
//		System.out.print("Solution: ");
//		new Part1(null);
//		System.out.print("\nSample Solution: ");
//		new Part1("sample.txt");
//		System.out.println();
//	}

	Part1(String filename) throws IOException {
		if (filename == null)
			filename = "input.txt";
		
		row = 1; col = 1;

		List<char[]> sequences = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
			.map(String::toCharArray)
			.collect(Collectors.toList());

		for (char[] seq : sequences) {
			for (char c : seq) {
				switch(c) {
					case 'U':
						row = normalize(row - 1);
						break;
					case 'R':
						col = normalize(col + 1);
						break;
					case 'D':
						row = normalize(row + 1);
						break;
					case 'L':
						col = normalize(col - 1);
						break;
					default:
						throw new IllegalArgumentException(c + " is not a supported move");
				}
			}
			System.out.print(board[row][col]);
		}
	}

	int normalize(int extreme) {
		return Math.max(0, Math.min(2, extreme));
	}
}
