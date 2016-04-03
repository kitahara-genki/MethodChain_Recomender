package lecture2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadsLines {

	public static void main(String[] args) throws IOException {
		File f = new File("datafiles/lecture2/input.txt");
		FileInputStream fis = new FileInputStream(f);
		
		// above is as before
		// below, instead of decorating with DataInputStream
		//   we will use BufferedReader
		BufferedReader d = new BufferedReader(new InputStreamReader(fis));
		
		// we would like to process the file one line at a time
		
		String s = d.readLine();
		System.out.println("We saw " + s);

	}

}
