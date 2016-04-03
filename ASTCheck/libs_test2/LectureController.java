package studio1.lecture;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;

public class LectureController extends JPanel implements ActionListener {

	private OpinionModel model;
	private ViewAsSlider view1, view2;
	private ViewAsText view3;
	
	public LectureController() {
		this.add(new JLabel("Controller"));
		this.add(new JLabel("and this"));
		this.add(new JLabel("something kind of long"));
                // add other things you want to see here:
		model = new OpinionModel();
		System.out.println("model is" + model);
		view1 = new ViewAsSlider(model, true);
		view2 = new ViewAsSlider(model, false);
		view3 = new ViewAsText(model);
		
		//
		// be sure to add the slider to the panel (this)
		//
		this.add(view1);
		add(view2);
		add(view3);
		setValue(7);
		JButton maxout = new JButton("Max!");
		maxout.addActionListener(this);
		add(maxout);
	}
	
	private void setValue(int val) {
		model.setValue(val);
		System.out.println("value now " + model.getValue());
	}
	
	public static void main(String[] args) {
		LectureController panel = new LectureController();
		//
		// What you see below is what YOPS did for you
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
		
		if (arg0.getActionCommand().equals("Max!"))
			setValue(10);
	}


}

