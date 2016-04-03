package lecture1s1;

public class Controller {
	
	public static void main(String[] args) {
		Cares oneCares = new Cares();
		AlsoCares twoCares = new AlsoCares();
		Balance mine = new Balance(oneCares, twoCares);
		
		mine.setBalance(1000); // should cause cares object to say something
	}

}
