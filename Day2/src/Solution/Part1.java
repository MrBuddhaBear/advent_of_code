package Solution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

	public static void main(String[] args) {
		File dir = new File(".");
		File fin = null;
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + "input.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dir);
		System.out.println(fin);
	}

	static class Document {
		
		String filname = "input.txt";
		
		private static void readFile(File file) throws IOException {
			// Construct BufferedReader from FileReader
			BufferedReader br = new BufferedReader(new FileReader(file));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		 
			br.close();
		}
	}

}
