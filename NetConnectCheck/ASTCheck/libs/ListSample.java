package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ListSample {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Listのサンプル");
		shell.setBounds(100, 100, 400, 100);
		// Listを作り、Selectionが変化した際に選択項目を表示します
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		list.add("1行目");
		list.add("2行目");
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String message = "選択項目の番号：";
				for (int i = 0; i < list.getSelectionCount(); i++) {
					message += " " + list.getSelectionIndices()[i];
				}
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage(message);
				messageBox.open();
			}
		});
		list.setSize(200, 50);
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
