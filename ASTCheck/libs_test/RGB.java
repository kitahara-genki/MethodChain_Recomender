/* 
* Lab 1 
* TA: Joseph Kwon 
* Grade: See Blackboard 
* Notes: don't forget javadocs next time
*/ 
package lab1;
import nip.*;
import lab1.percent.Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RGB extends JPanel {

	final private Controller red, blue, green;
	public boolean lock_state; 

	/**
	 * I'll talk about "final" below in lecture next week -- RKC
	 * 
	 * @param one An image from which pixels will be copied
	 * @param two An image, same size as one, to which pixels will be copied
	 */
	public RGB(final Image one, final Image two) {
		lock_state = false;
		add(red   = new Controller("red"));
		add(blue  = new Controller("blue"));
		add(green  = new Controller("green"));
		//
		//  Add a green and blue Controller to this JPanel, too
		//  Until you do that, you'll get null pointer exceptions if you hit "go"
		//

		JButton go = new JButton("go");
		this.add(go);

		//
		// When "go" button is pressed, call copyImage() to do the work
		//  This will fail until you initialize red, green, and blue
		//  The code below uses an anonymous inner class, which I will
		//  explain in upcoming lectures.
		//

		go.addActionListener(
				new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						copyImage(one, two);
					}
					
				}
		);
	
//		JButton lock = new JButton("Lock");
//		this.add(lock);
//		
//		lock.addActionListener(
//				new ActionListener(){
//					public void actionPerformed(ActionEvent e) {
//						lock_state = !lock_state;
//						
//					}
//				}
//			);
//		
//		if (lock_state){
//			red.getModel().setValue(blue.getModel().getValue());
//		}
		
		red.getModel().addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						copyImage(one, two);
					}		
				}
		);

		blue.getModel().addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						copyImage(one, two);
					}		
				}
		);
		
		green.getModel().addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						copyImage(one, two);
					}		
				}
		);
		
	}

	/**
	 * Copy the in image to the out image.
	 * Each pixel is broken into its red, green, blue components.
	 * The appropriate percentage is taken of each color and combined into
	 *    a new color, written to the out image.
	 */
	public void copyImage(Image in, Image out) {
		for (int i=0; i < in.getWidth(); ++i) {
			for (int j=0; j < in.getHeight(); ++j) {

				Color c = in.getPixelColor(i, j);

				int r = red.getModel().computePercentOf(c.getRed());
				int g = green.getModel().computePercentOf(c.getGreen());
				int b = blue.getModel().computePercentOf(c.getBlue());

				out.setPixel(i, j, new Color(r, g, b));
			}
		}
		out.repaint();  // force the changes to get shown

	}

	public static void main(String[] args) {
		/*
		 * picture by Melanie Cytron http://macytron.deviantart.com/
		 */
		NIP nip = new NIP(600, 400, 2, "images/Summit_Lake_II_by_macytron.jpg");
		RGB panel = new RGB(
				nip.getGraphicsPanel(0).getMainImage(),
				nip.getGraphicsPanel(1).getMainImage()
		);

		//
		// What you see below is what NIP did for you
		//
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
