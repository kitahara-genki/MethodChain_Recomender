package lecture1s2;

import java.awt.Color;

import javax.swing.DefaultBoundedRangeModel;

public class MYear extends DefaultBoundedRangeModel {
	// extends is the "is-a" gesture in object oriented languages
	//   MYear is a DefaultBOudnedRangeModel with perhaps more stuff
	//    year comes with a color
	//
	private Color color;
	
	public MYear() {
		super(2015, 0, -300, 2020);
		color = Color.RED;
	}
	
	// because we're extending that class, we get all of its methods in this class too
	
	public static void main(String[] args) {
		MYear year = new MYear();
		System.out.println("Year is " + year);
		// because of "extends", we can do things to year !!
		year.setValue(2017);
		System.out.println("Year is now " + year);
		System.out.println("Modell says year is " + year.getValue());
	}

}
