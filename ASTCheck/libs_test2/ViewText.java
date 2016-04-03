package studio1.percent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.BoundedRangeModel;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewText 
   extends JFormattedTextField
   implements ActionListener, ChangeListener {

	static Font BIG_FONT = new Font("sans serif", Font.BOLD, 24);

	private BoundedRangeModel model;

	public ViewText(BoundedRangeModel model) {
		super(NumberFormat.getIntegerInstance());
		this.model = model;
		setValue(new Integer(model.getValue()));
		setPreferredSize(new Dimension(80,40));
		setFont(BIG_FONT);
		addActionListener(this);
		model.addChangeListener(this);
	}

	// View, called as ChangeListener, when the value changes
	public void stateChanged(ChangeEvent ce) {
		setText("0"+model.getValue());
	}

	// Controller, called as ActionListener (push ENTER)
	public void actionPerformed(ActionEvent ae) {
		try {
			model.setValue(Integer.parseInt(getText()));
		} catch (NumberFormatException nfe) {
			//no change
		}
		setText(""+model.getValue());
	}
}

