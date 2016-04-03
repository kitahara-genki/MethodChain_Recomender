package lecture1s2;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Views {
	
	public static void main(String[] arsg) {
		JFrame main = new JFrame();
		JPanel panel = new JPanel();
		main.add(panel);
		
		MYear year = new MYear();
		
		year.addChangeListener(new Announcer());  // it is also a view of this models
		
		JSlider slider = new JSlider(year);  // slider is now a view of that model
		JSlider slider2 = new JSlider(year);
		panel.add(new JLabel("one view:"));
		panel.add(slider);
		panel.add(new JLabel("another view"));
		panel.add(slider2);
		
		
		main.pack();
		main.setVisible(true);
	}

}
