package jp.sf.amateras.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RowLayoutSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("RowLayoutのサンプル");
		shell.setBounds(100, 100, 400, 200);
		// RowLayoutを作成し、オプションを設定します。
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.marginTop = 0; // デフォルトは3
		layout.marginLeft = 0; // デフォルトは3
		layout.marginBottom = 0; // デフォルトは3
		layout.marginRight = 0; // デフォルトは3
		layout.spacing = 0; // デフォルトは3
		shell.setLayout(layout);
		// Shellにウィジェットをいくつか追加します
		for (int i = 0; i < 30; i++) {
			Button button = new Button(shell, SWT.PUSH);
			button.setText(i + "番目");
		}
		// ShellオブジェクトをOpenします
		shell.open();
		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
