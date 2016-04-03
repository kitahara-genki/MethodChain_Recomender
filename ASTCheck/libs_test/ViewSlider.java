package studio1.percent;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;

public class ViewSlider extends JSlider {


	public ViewSlider(BoundedRangeModel model, boolean snap) {
		super(model);
		this.setMajorTickSpacing(20);
		this.setMinorTickSpacing(10);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		setSnapToTicks(snap);
	}


}
