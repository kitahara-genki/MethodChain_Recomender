package jp.sf.amateras.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class GraphicsSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Graphicsパッケージのサンプル");
		shell.setLayout(new GridLayout());
		// Colorオブジェクトを作ります
		Color redColor = new Color(display, 255, 0, 0);
		// Cursorオブジェクトを作ります
		Cursor waitCursor = new Cursor(display, SWT.CURSOR_WAIT);
		// Labelオブジェクトを作ります。
		Label label = new Label(shell, SWT.CENTER | SWT.BORDER);
		label.setText("Labelではテキストを表示します");
		label.setLayoutData(new GridData(GridData.FILL_BOTH));
		// LabelにColor、Cursorをセットします
		label.setForeground(redColor);
		label.setCursor(waitCursor);
		// ShellオブジェクトをOpenします。
		shell.setSize(240, 80);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// Graphicsオブジェクトを破棄します
		redColor.dispose();
		waitCursor.dispose();
		// Displayオブジェクトを破棄します
		display.dispose();
	}
}
