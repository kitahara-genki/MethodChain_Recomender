package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class GroupSample {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Groupのサンプル");
		shell.setBounds(100, 100, 400, 200);
		shell.setLayout(new GridLayout());
		// Groupを作成して、ラジオボタンを2つ配置します
		final Group group = new Group(shell, SWT.NONE);
		group.setText("Group1");
		group.setLayout(new FillLayout());
		Button item1 = new Button(group, SWT.RADIO);
		item1.setText("選択肢1");
		Button item2 = new Button(group, SWT.RADIO);
		item2.setText("選択肢2");

		// Buttonを追加して、押下された際に選択されているボタンを取得します。
		Button button = new Button(shell, SWT.PUSH);
		button.setText("選択項目を取得");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String message = "";
				for (int i = 0; i < group.getChildren().length; i++) {
					if (group.getChildren()[i] instanceof Button) {
						Button bt = (Button) group.getChildren()[i];
						message += bt.getText() + "の選択：" + bt.getSelection() + "\n";
					}
				}
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage(message);
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
		display.dispose();
	}
}
