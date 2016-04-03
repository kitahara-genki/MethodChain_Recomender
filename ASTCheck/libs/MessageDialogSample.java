package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);

		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		// ダイアログのタイトルを設定
		messageBox.setText("MessageBox");
		// メッセージを設定
		messageBox.setMessage("MessageBoxの使用例");
		if (messageBox.open() == SWT.YES) {
			// 「はい」が押された場合の処理
		}

		display.dispose();
	}
}
