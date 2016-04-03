package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;

public class FontDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);

		FontDialog fontDialog = new FontDialog(shell, SWT.NULL);
		// 選択されたフォントの情報を取得
		FontData data = fontDialog.open();

		display.dispose();
	}
}
