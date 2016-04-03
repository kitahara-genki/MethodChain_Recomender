package lecture2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AlsoReadsFile {

	public static void main(String[] args) throws IOException {
		File f = new File("datafiles/lecture2/input.txt");
		FileInputStream fis = new FileInputStream(f);
		// this time, we'll build something more sophisticated
		//   on top of the FileInputStream
		// This is called a Decorator (pattern)
		//   at the botton, we have a simple FileInputStream
		//   On top of that, we build a DataInputStream
		//    which is able to assemble the bytes of the FileInputStream
		//    into:  integers, floats, .... 
		
		DataInputStream dis = new DataInputStream(fis);
		
		// now we can treat the file at a "higher" level
		
		while (dis.available() > 0) {
			// file is not exhausted yet
			byte b = dis.readByte();
			System.out.println("Byte is " + b + " char " + (char)b);
			// the readChar method takes in *two* bytes and renders
			//  them as a single character.  Two bytes are used
			//  so that richer alphabets can be accommodated, such
			//  as Hebrew, Greek, Chinese, etc.
			//
//			char c = dis.readChar();
//			System.out.println("we got " + c);
		}

	}

}
