package jp.sf.amateras.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GridLayoutSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("GridLayoutのサンプル");
		shell.setBounds(100, 100, 500, 150);
		shell.setLayout(new GridLayout(2, false));
		// Shellにウィジェットをいくつか追加します
		// 横2Grid分を結合したLabelを作成します
		Label topLabel = new Label(shell, SWT.BORDER);
		topLabel.setText("横2つのGridを結合します。");
		GridData topGrid = new GridData();
		topGrid.horizontalSpan = 2;
		topLabel.setLayoutData(topGrid);
		// 縦2Grid分結合したLabelも作成します
		Label leftLabel = new Label(shell, SWT.BORDER | SWT.WRAP);
		leftLabel.setText("縦2つの\nGridを結合します。");
		GridData leftGrid = new GridData();
		leftGrid.verticalSpan = 2;
		leftLabel.setLayoutData(leftGrid);
		// ウィンドウの幅に合わせて大きさが変化するようにTextを配置します
		Text upperText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		upperText.append("ウィンドウの幅に合わせて伸張します。");
		GridData upperGrid = new GridData();
		upperGrid.horizontalAlignment = GridData.FILL;
		upperGrid.grabExcessHorizontalSpace = true;
		upperText.setLayoutData(upperGrid);
		// デフォルトのGridDataでTextを追加します(大きさは変わりません)
		Text lowerText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		lowerText.setText("GridDataをセットしないと標準のGridDataが適用されます。");
		// ShellオブジェクトをOpenします
		shell.open();
		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
