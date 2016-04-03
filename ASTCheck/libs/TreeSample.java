package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeSample {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Treeのサンプル");
		shell.setBounds(100, 100, 400, 200);
		// Treeを作ります。
		final Tree tree = new Tree(shell, SWT.BORDER | SWT.MULTI);
		tree.setSize(200, 100);
		// TreeItemを追加します(親ノード1つに子ノード2つ)
		TreeItem parent = new TreeItem(tree, SWT.NONE);
		parent.setText("親ノード");
		TreeItem child1 = new TreeItem(parent, SWT.NONE);
		child1.setText("子ノード1");
		TreeItem child2 = new TreeItem(parent, SWT.NONE);
		child2.setText("子ノード2");
		parent.setExpanded(true); // Treeは開いた状態にします。
		// TreeItemを追加します(2つ目の親ノード)
		TreeItem parent2 = new TreeItem(tree, SWT.NONE);
		parent2.setText("2つ目の親ノード");
		// Buttonを追加して押下されたときTreeで選ばれている項目を取得します。
		Button button = new Button(shell, SWT.PUSH);
		button.setText("選択されている項目を表示");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String message = "選択項目の番号:";
				for (int i = 0; i < tree.getSelectionCount(); i++) {
					message += " " + tree.getSelection()[i].getText();
				}
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage(message);
				messageBox.open();
			}
		});
		button.setBounds(0, 110, 150, 20);
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
