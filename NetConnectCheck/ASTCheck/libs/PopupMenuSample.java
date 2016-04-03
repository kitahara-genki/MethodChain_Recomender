package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class PopupMenuSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("PopupMenuのサンプル");
		shell.setBounds(100, 100, 400, 120);
		// ポップアップメニューを作成します
		Menu menu = new Menu(shell, SWT.POP_UP);
		shell.setMenu(menu);
		// 1つ目のメニュー項目を作成します(普通のメニュー項目)
		MenuItem item1 = new MenuItem(menu, SWT.PUSH);
		item1.setText("普通のメニュー");
		item1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("普通のメニューが選択されました。");
			}
		});
		// セパレータを追加します
		new MenuItem(menu, SWT.SEPARATOR);
		// 2つ目のメニュー項目を作成します(チェック付きのメニュー項目)
		final MenuItem item2 = new MenuItem(menu, SWT.CHECK);
		item2.setText("チェック付きのメニュー");
		item2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (item2.getSelection()) {
					System.out.println("選択されています。");
				} else {
					System.out.println("選択されていません。");
				}
			}
		});
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
