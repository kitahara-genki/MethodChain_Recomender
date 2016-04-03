package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class TabFolderSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("TabFolderのサンプル");
		shell.setBounds(100, 100, 400, 200);
		// TabFolderを作ります
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setSize(300, 150);
		// 1つめのタブを作ります
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem1.setText("1つめ");
		Text text = new Text(tabFolder, SWT.V_SCROLL); // 親にはTabFolderを指定する
		text.append("テキストを入力します");
		tabItem1.setControl(text); // setControlでItemにTextをセット
		// 2つめのタブを作ります
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("2つめ");
		Button button = new Button(tabFolder, SWT.PUSH);
		button.setText("ボタン");
		tabItem2.setControl(button);
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
