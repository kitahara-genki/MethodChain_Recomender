package lecture1s2;

public class Year {
	
	private int year;
	private CaresAboutYear1 cay1;
	private AlsoCares ac;
	
	//without MVC
	// everytime a new "carer" comes along
	//  we have to add an instance var, parameter for the constructor, etc.
	//
	
	public Year(int year, CaresAboutYear1 cay1, AlsoCares ac) {
		this.year = year;
		this.cay1 = cay1;
		this.ac = ac;
	}
	
	public void setYear(int newYear) {
		this.year = newYear;
		cay1.actWhenChanged();
		ac.itchanged();
	}
	
	public static void main(String args[]) {
		CaresAboutYear1 cares = new CaresAboutYear1();
		AlsoCares carestoo = new AlsoCares();
		Year now = new Year(2015, cares, carestoo);
		now.setYear(2016);
	}

}
