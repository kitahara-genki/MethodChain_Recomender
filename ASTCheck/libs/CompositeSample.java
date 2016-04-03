package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class CompositeSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Compositeのサンプル");
		shell.setLayout(new GridLayout(2, false));

		// ShellにListを追加
		List list = new List(shell, SWT.BORDER);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		list.setItems(new String[]{"Eclipse", "NetBeans"});

		// Compositeを作り、ボタンを2つ追加
		Composite composite = new Composite(shell, SWT.NULL);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		Button button1 = new Button(composite, SWT.PUSH);
		button1.setText("追加");

		Button button2 = new Button(composite, SWT.PUSH);
		button2.setText("削除");

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
