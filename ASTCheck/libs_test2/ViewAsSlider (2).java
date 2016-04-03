package studio1.lecture;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;

public class ViewAsSlider extends JSlider {


	public ViewAsSlider(BoundedRangeModel model, boolean snap) {
		super(model);
		//this.setMajorTickSpacing(5);
		this.setMinorTickSpacing(1);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		setSnapToTicks(snap);
	}


}

