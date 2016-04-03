package lecture1s1;
import javax.swing.DefaultBoundedRangeModel;

// a model for a balance

// "extends" is the "is-a" gesture  -- everything DefaultBoundedRangeModel
//   is, and perhaps more
public class MBalance extends DefaultBoundedRangeModel {
	
	private String accountName;
	
	public MBalance() {
		// because we extend, we use super(....) to initialize the superclass
		//  actually calls a constructor from DefaultBoundedRangeModel
		super(100, 0, 0, 10000);
	}

}
