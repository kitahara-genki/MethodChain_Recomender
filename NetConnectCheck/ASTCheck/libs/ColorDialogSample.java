package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ColorDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);
		ColorDialog colorDialog = new ColorDialog(shell, SWT.NULL);
		// ダイアログのタイトルを設定
		colorDialog.setText("ColorDialog");
		// 選択された色を取得
		RGB rgb = colorDialog.open();
		display.dispose();
	}
}
