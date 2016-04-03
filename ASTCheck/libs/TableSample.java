package jp.sf.amateras.swt.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Tableのサンプル");
		shell.setBounds(100, 100, 400, 100);
		// Tableを作ります
		Table table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		// カラムをセットします
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setText("No.");
		column1.setWidth(40);
		TableColumn column2 = new TableColumn(table, SWT.NONE);
		column2.setText("名前");
		column2.setWidth(50);
		TableColumn column3 = new TableColumn(table, SWT.NONE);
		column3.setText("説明");
		column3.setWidth(250);
		// データをセットします
		TableItem item1 = new TableItem(table, SWT.NONE);
		item1.setText(0, "1");
		item1.setText(1, "名前1");
		item1.setText(2, "1行目の項目の説明です");
		TableItem item2 = new TableItem(table, SWT.NONE);
		item2.setText(0, "2");
		item2.setText(1, "名前2");
		item2.setText(2, "2行目の項目の説明です");
		// Tableのサイズを再調整します
		table.setSize(table.computeSize(SWT.DEFAULT, 50));
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
