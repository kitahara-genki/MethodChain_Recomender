package lecture1s1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Bank {

	public static void main(String[] args) {
		MBalance ron = new MBalance();
		
		System.out.println("your balance is " + ron.getValue());
		
		ron.setValue(1000);
		
		System.out.println(" and now " + ron.getValue());
		
		// some views
		
		JSlider slider = new JSlider(ron);  // sliders know about models
		
		JFrame mframe = new JFrame();
		JPanel panel = new JPanel();
		mframe.add(panel);
		
		panel.add(slider);
		
		JSlider slider2 = new JSlider(ron);
		panel.add(slider2);
		
		// add something that prints the value when it changes
		Printer printer = new Printer();
		ron.addChangeListener(printer);
		
		
		mframe.pack();
		mframe.setVisible(true);
		
		

	}

}
