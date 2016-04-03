package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DirectoryDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);
		DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.NULL);
		// ダイアログのタイトルを設定
		directoryDialog.setText("DirectoryDialog");
		// メッセージを設定
		directoryDialog.setMessage("DirectoryDialogの使用例");
		// フォルダのパスを取得
		String folderName = directoryDialog.open();

		display.dispose();
	}
}
