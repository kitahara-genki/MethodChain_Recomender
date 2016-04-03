package jp.sf.amateras.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class FillLayoutSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("FillLayoutのサンプル");
		shell.setBounds(100, 100, 400, 150);
		// FillLayoutを作成し、オプションを設定します。
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.marginHeight = 10; // デフォルトは0
		layout.marginWidth = 10; // デフォルトは0
		layout.spacing = 5; // デフォルトは0
		shell.setLayout(layout);
		// Shellにウィジェットをいくつか追加します
		Button button = new Button(shell, SWT.PUSH);
		button.setText("1番目");
		Label label = new Label(shell, SWT.BORDER);
		label.setText("2番目");
		// ShellオブジェクトをOpenします
		shell.open();
		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
