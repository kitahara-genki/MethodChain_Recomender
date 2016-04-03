package lecture1s1;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Printer implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		// gets called by the model as a subscriber
		System.out.println("Change! " + e);
		
	}

}
