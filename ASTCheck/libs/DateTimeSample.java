package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DateTimeSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Labelのサンプル");
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		// DateTimeオブジェクトを作ります。
		// DATE
		Label label = new Label(shell, SWT.NONE);
		label.setText("SWT.DATE");
		DateTime dateTime1 = new DateTime(shell, SWT.DATE | SWT.LONG); // SWT.LONGでyyyy年mm月dd日表記になる
		dateTime1.setDate(2008, 0, 1);

		// TIME
		Label label2 = new Label(shell, SWT.NONE);
		label2.setText("SWT.TIME");
		DateTime dateTime2 = new DateTime(shell, SWT.TIME);
		dateTime2.setTime(13, 5, 20);

		// CALENDAR
		Label label3 = new Label(shell, SWT.NONE);
		label3.setText("SWT.CALENDAR");
		DateTime dateTime3 = new DateTime(shell, SWT.CALENDAR);
		dateTime3.setDate(2008, 11, 31);


		// ShellオブジェクトをOpenします。
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
