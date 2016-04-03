package studio1.percent;

import javax.swing.DefaultBoundedRangeModel;

public class PercentModel extends DefaultBoundedRangeModel {
	public PercentModel() {
		super(100, 0, 0, 100);
	}
	
	public int computePercentOf(int n){
		
			return(int) ((this.getValue()/100.0)*n);
	
	}
}
