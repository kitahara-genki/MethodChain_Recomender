package lecture1s1;

public class Balance {
	
	private int amount;
	private Cares cares;
	private AlsoCares caresToo;
	
	public Balance(Cares cares, AlsoCares caresToo) {
		this.amount = 100; // start with 100 dollars
		this.cares = cares;
		this.caresToo = caresToo;
	}
	
	public void setBalance(int newBalance) {
		this.amount = newBalance;
		cares.balanceChanged();
		caresToo.tellMeAboutIt();
	}

}
