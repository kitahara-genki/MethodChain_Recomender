package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ButtonSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Buttonのサンプル");
		// shell.setBounds(100, 100, 400, 100);
		shell.setLayout(new GridLayout());
		// Buttonオブジェクトを作ります。
		final Button button = new Button(shell, SWT.PUSH);
		button.setText("ボタンを押してください");
		// Buttonオブジェクトにイベントを登録します。
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				button.setText("ボタンが押されました");
			}
		});

		// チェックボックス
		Button check = new Button(shell, SWT.CHECK);
		check.setText("チェックボックス");

		// ラジオボタン
		Button radio = new Button(shell, SWT.RADIO);
		radio.setText("ラジオボタン");

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
