package lecture1s1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SimpleFrame {

	public static void main(String[] args) {
		JFrame mframe = new JFrame();
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("annoying beep"));
		panel.add(new JLabel("bad beep, go away"));
		panel.add(new JLabel("what happened to the other labels?"));
		panel.add(new JSlider());
		
		panel.add(new JButton("Push to end class now"));
		
		// Frames can only keep one thing, so we'll use the panel
		//   we can add lots of stuff to the panel !
		
		mframe.add(panel);
		//
		// Swing won't organize things in the window
		//  until we say we are ready:
		//
		mframe.pack();
		//
		// We need the following, or swing is shy
		//   and won't show us anything
		//
		mframe.setVisible(true);

	}

}
