package Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {

	static char[][] board = {{ 0 , 0 ,'1', 0 , 0 },
							 { 0 ,'2','3','4', 0 },
							 {'5','6','7','8','9'},
							 { 0 ,'A','B','C', 0 },
							 { 0 , 0 ,'D', 0 , 0 }};

	int row, col;

	public static void main(String[] args) throws IOException {
		System.out.print("Solution: ");
		new Part2(null);
		System.out.print("\nSample Solution: ");
		new Part2("sample.txt");
		System.out.println();
	}

	Part2(String filename) throws IOException {
		if (filename == null)
			filename = "input.txt";
		
		row = 2; col = 0;

		List<char[]> sequences = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
			.map(String::toCharArray)
			.collect(Collectors.toList());

		for (char[] seq : sequences) {
			for (char c : seq) {
				switch(c) {
					case 'U':
						update(true, -1);
						break;
					case 'R':
						update(false, 1);
						break;
					case 'D':
						update(true, 1);
						break;
					case 'L':
						update(false, -1);
						break;
					default:
						throw new IllegalArgumentException(c + " is not a supported move");
				}
			}
			System.out.print(board[row][col]);
		}
	}

	void update(boolean updateRow, int change) {
		int tempRow = row;
		int tempCol = col;
		if (updateRow) {
			tempRow = normalize(tempRow + change);
		} else {
			tempCol = normalize(tempCol + change);
		}
		if (board[tempRow][tempCol] != 0) {
			row = tempRow;
			col = tempCol;
		}
	}

	int normalize(int extreme) {
		return Math.max(0, Math.min(4, extreme));
	}
}