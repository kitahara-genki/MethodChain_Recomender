package jp.sf.amateras.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class StackLayoutSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("StackLayoutのサンプル");
		shell.setBounds(100, 100, 400, 150);
		// Groupを作成してStackLayoutを設定します
		final Group group = new Group(shell, SWT.NONE);
		group.setText("StackLayout");
		group.setBounds(10, 10, 200, 50);
		final StackLayout layout = new StackLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		group.setLayout(layout);
		// GroupにLabelとButton(ラジオボタン)を作成します
		final Label label = new Label(group, SWT.BORDER | SWT.CENTER);
		label.setText("1番目はLabel");
		final Button radioButton = new Button(group, SWT.RADIO | SWT.BORDER);
		radioButton.setText("2番目はRadioButton");
		radioButton.setSelection(true);
		// 一番手前にLabelをセットします
		layout.topControl = label;
		group.layout(); // セットした内容を反映するためにlayout()を呼び出します
		// 手前のウィジェットを切り替えるためのButtonを追加します
		Button button = new Button(shell, SWT.PUSH);
		button.setText("切り替え");
		button.setBounds(10, 70, 100, 20);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (layout.topControl.equals(label)) {
					layout.topControl = radioButton;
				} else {
					layout.topControl = label;
				}
				group.layout(); // 切り替えを反映するためにlayout()を呼び出します
			}
		});
		// ShellオブジェクトをOpenします
		shell.pack();
		shell.open();
		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
