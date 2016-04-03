package lecture1s2;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Announcer implements ChangeListener {
	
	public Announcer() {
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		System.out.println("Something chnaged! " + e);
		
	}

}
