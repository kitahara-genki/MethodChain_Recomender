package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ComboSample {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Comboのサンプル");
		shell.setBounds(100, 100, 400, 100);
		// Comboを作ります
		final Combo combo = new Combo(shell, SWT.READ_ONLY);
		// combo.setItems (new String [] {"1番目", "2番目", "3番目"});
		combo.add("1番目");
		combo.add("2番目");
		combo.add("3番目");
		combo.setText("2番目"); // 2番目を初期選択にします
		combo.setBounds(10, 10, 100, 20);
		// Buttonを追加し、押下された際にComboの選択項目を表示します
		Button button = new Button(shell, SWT.PUSH);
		button.setText("選択項目のIndexを取得");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage("項番：" + combo.getSelectionIndex() + "が選択されています。");
				messageBox.open();
			}
		});
		button.setBounds(120, 10, 150, 20);
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
