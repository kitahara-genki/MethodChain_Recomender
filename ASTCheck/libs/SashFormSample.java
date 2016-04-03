package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SashFormSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("SashFormのサンプル");
		// shell.setBounds(100, 100, 400, 200);
		shell.setLayout(new FillLayout());

		// // SashFormを作ります。
		// SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		// sashForm.setSize(400, 200);
		// // 上のエリアはTextを配置
		// Text text = new Text(sashForm, SWT.V_SCROLL);
		// text.append("テキストを入力します");
		// // 下のエリアはラベルとボタンのCompositeを配置
		// Composite composite = new Composite(sashForm, SWT.NONE);
		// Label label = new Label(composite, SWT.BORDER);
		// label.setText("Label");
		// label.setBounds(10, 10, 100, 20);
		// Button button = new Button(composite, SWT.PUSH);
		// button.setText("Button");
		// button.setBounds(120, 10, 50, 20);
		// // 各フォームの割合を指定します
		// sashForm.setWeights(new int[] { 4, 1 });

		// SashFormを作ります。
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		shell.setSize(250, 180);

		// 上のエリアはTextを配置
		Text text = new Text(sashForm, SWT.V_SCROLL);
		text.append("テキストを入力します");

		// 下のエリアはTreeを配置
		Tree tree = new Tree(sashForm, SWT.BORDER);
		tree.setSize(200, 100);
		// TreeItemを追加します(親ノード1つに子ノード2つ)
		TreeItem parent = new TreeItem(tree, SWT.NONE);
		parent.setText("親ノード");
		TreeItem child1 = new TreeItem(parent, SWT.NONE);
		child1.setText("子ノード1");
		TreeItem child2 = new TreeItem(parent, SWT.NONE);
		child2.setText("子ノード2");
		parent.setExpanded(true); // Treeは開いた状態にします。
		// 上下の割合を指定します
		sashForm.setWeights(new int[] { 1, 2 });

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
