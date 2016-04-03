package jp.sf.amateras.swt.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PrintDialogSample {
	public static void main(String[] args) {
		// Displayオブジェクトを作ります -----(1)
		Display display = new Display();
		// Shellオブジェクトを作ります -----(2)
		final Shell shell = new Shell(display);

		PrintDialog printDialog = new PrintDialog(shell, SWT.NULL);
		// プリンタの情報を取得
		PrinterData data = printDialog.open();


		display.dispose();
	}
}
