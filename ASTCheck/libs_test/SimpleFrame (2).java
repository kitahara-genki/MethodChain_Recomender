package lecture1s2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SimpleFrame {
	
	public static void main(String[] args) {
		JFrame mframe = new JFrame();
		// what's below doesn't work because the Frame remembers only
		//  the last thing added to it
//		mframe.add(new JLabel("hello"));
//		mframe.add(new JLabel("bye"));
//		mframe.add(new JLabel("foo"));
//		// OK let swing arrange what's in the frame
//		// otherwise, you'd have to resizse the frame to see things
		JPanel panel = new JPanel();
		panel.add(new JLabel("first thing"));
		panel.add(new JButton("Push me to end class!"));
		panel.add(new JLabel("some other thing"));
		mframe.add(panel);
		mframe.pack();
		// must make it visible for you to see it
		mframe.setVisible(true);
	}

}
