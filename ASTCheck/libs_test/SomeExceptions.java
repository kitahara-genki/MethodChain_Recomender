package lecture2;

public class SomeExceptions {

	public static void main(String[] args) {


		try {
			Object foo = null;
			int zero = 3;
			zero  = zero - 2;
			int thing = 5 / zero;

			System.out.println("Value of thing is " + thing);
			
			System.out.println("Foo is " + foo.toString());
		}
		
		catch (RuntimeException npe) {
			System.out.println("You had some kind of runtime exception");
			System.out.println(npe.getClass());
			npe.printStackTrace();
		}
		System.out.println("And now we continue");

	}

}
