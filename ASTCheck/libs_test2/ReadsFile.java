package lecture2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadsFile {

	public static void main(String[] args) throws IOException {
		File f = new File("datafiles/lecture2/input.txt");
//		File f = new File("lecture/lecture2/ReadsFile.java");
		FileInputStream fis = new FileInputStream(f);
		
		// as we read from fis, we get each character,
		//   one at a time, but they come to us
		//   as ints.   If the int is -1, the file is exhausted,
		//   we've reached end-of-file
		//   Otherwise, the int is an encoding of a character in
		//     the file, and if we cast it to (char) we can print
		//     it and see what it is
		
		int b = fis.read();
		String wholething = "";
		while (b != -1) {  // b is next char in file
			char c = (char) b;
			System.out.println("I saw " + c);
			wholething = wholething + c;
			b = fis.read();
		}
		
		System.out.println("Whole thing: " + wholething);
		System.out.println("It is done");
	}

}
