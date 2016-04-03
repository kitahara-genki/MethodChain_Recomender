package studio1.percent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import studio1.lecture.OpinionModel;
import studio1.lecture.ViewAsSlider;
import studio1.lecture.ViewAsText;

public class Controller extends JPanel implements ActionListener {

	private PercentModel model;
	private ViewSlider view1, view2;
	private ViewText view3;

	
	public Controller(String newString) {
		this.add(new JLabel("Controller"));
                // add other things you want to see here:
		this.add(new JLabel("something funny"));
		this.add(new JLabel(newString));
		model = new PercentModel();
		System.out.println("model is" + model);
		
		view1 = new ViewSlider(model, true);
		view2 = new ViewSlider(model, false);
		view3 = new ViewText(model);

		
		this.add(view1);
		add(view2);
		add(view3);
		setValue(100);
		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
		add(reset);
		JButton mid = new JButton("Mid");
		mid.addActionListener(this);
		add(mid);
		
	}
	
	private void setValue(int val) {
		model.setValue(val);
		System.out.println("value now " + model.getValue());
	}
	
	public static void main(String[] args) {
		Controller panel = new Controller("This is our string");
		//
		// What you see below is what 
		// Sedewick's code did for you in 131
		// When you create a new JFrame, by default it can't be seen
		//   It's a bit surprising at first, and
		//     not a good thing when you are starting out
		//
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
//		System.out.println("something happened: " 
//				+ arg0.getActionCommand());
		
		if (arg0.getActionCommand().equals("Reset"))
			setValue(0);
		
		else if (arg0.getActionCommand().equals("Mid"))
			setValue(50);
	}
	
}

