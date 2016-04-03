package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Textのサンプル");
		shell.setBounds(100, 100, 400, 200);
		// Text(複数行)を作ります
		Text text = new Text(shell, SWT.BORDER | SWT.V_SCROLL);
		text.setSize(200, 50);
		text.append("1行目\n"); // Textにデータを追加します
		text.append("2行目\n");
		// Text(単一行)をつくります
		Text singleText = new Text(shell, SWT.BORDER | SWT.SINGLE);
		singleText.setBounds(0, 60, 200, 20);
		singleText.append("11111\n"); // Textにデータを追加します
		singleText.append("22222\n"); // SingleTextでは改行は無視されます
		// VerifyListenerの追加
		singleText.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				// 数字以外は受け付けない
				for (int i = 0; i < e.text.length(); i++) {
					if (Character.isDigit(e.text.charAt(i)) == false) {
						e.doit = false;
					}
				}
			}
		});
		// ShellオブジェクトをOpenします。
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
