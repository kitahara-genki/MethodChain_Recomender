package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class LabelSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Labelのサンプル");
		shell.setBounds(100, 100, 400, 100);
		// Labelオブジェクトを作ります。
		Label label = new Label(shell, SWT.LEFT);
		label.setText("Labelではテキストを表示します");
		label.setSize(200, 50);
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
