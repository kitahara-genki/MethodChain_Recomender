package jp.sf.amateras.swt.thread;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ThreadSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		final Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		Shell shell = new Shell(display);
		shell.setText("SWTのサンプル");
		shell.setBounds(100, 100, 400, 100);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		// Labelオブジェクトを作成します -----(3)
		final Label label = new Label(shell, SWT.BORDER);
		label.setText("ボタンを押してください");
		// Buttonオブジェクトを作成します -----(3)
		Button button = new Button(shell, SWT.PUSH);
		button.setText("ボタン");
		// Buttonオブジェクトにイベントリスナを定義します -----(4)
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				// スレッド処理を呼び出します。
				Thread thread = new Thread() {
					public void run() {
						// 別スレッドからUIの設定を変更
						display.asyncExec(new Runnable() {
							public void run() {
								label.setText("別スレッドからLabelテキストを設定");
							}
						});
					}
				};
				thread.start();
			}
		});
		// ShellオブジェクトをOpenします -----(5)
		shell.open();
		// 終了するまでループします -----(6)
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// オブジェクトを解放します -----(7)
		display.dispose();
	}
}
