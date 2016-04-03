package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolbarSample {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Toolbarのサンプル");
		shell.setBounds(100, 100, 400, 200);
		// ToolBarを作成します
		ToolBar toolBar = new ToolBar(shell, SWT.NONE);
		toolBar.setSize(300, 100);
		// 項目1(テキストのみ)を作成します
		final ToolItem item1 = new ToolItem(toolBar, SWT.PUSH);
		item1.setText("項目1");
		item1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage(item1.getText() + "が選択されました。");
				messageBox.open();
			}
		});
		// 項目2(イメージとテキスト)を作成します
		final ToolItem item2 = new ToolItem(toolBar, SWT.PUSH);
		Image image = new Image(display, ToolbarSample.class.getResourceAsStream("sample.gif"));
		item2.setText("項目2");
		item2.setImage(image);
		item2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage(item2.getText() + "が選択されました。");
				messageBox.open();
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
		image.dispose();
		display.dispose();
	}
}
