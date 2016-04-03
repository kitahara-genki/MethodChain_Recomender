package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);

		// ファイルを開くダイアログを作成
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		// フィルタを設定
		fileDialog.setFilterNames(new String[] { "Javaソースファイル(.java)", "すべて" });
		fileDialog.setFilterExtensions(new String[] { "*.java", "*.*" });
		// ダイアログのタイトルを設定
		fileDialog.setText("FileDialog");
		// ファイル名を取得
		String fileName = fileDialog.open();

		display.dispose();
	}
}
