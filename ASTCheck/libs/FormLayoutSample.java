package jp.sf.amateras.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FormLayoutSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("FormLayoutのサンプル");
		shell.setBounds(100, 100, 500, 200);
		shell.setLayout(new FormLayout());
		// Shellにウィジェットをいくつか追加します
		// 上部にLabelを追加します
		Label topLabel = new Label(shell, SWT.BORDER);
		topLabel.setText("上にLabelを配置します。");
		FormData topForm = new FormData();
		topForm.top = new FormAttachment(0, 1);
		topForm.left = new FormAttachment(0, 1);
		topForm.right = new FormAttachment(100, -1);
		topLabel.setLayoutData(topForm);
		// 左側にLabelを追加します
		Label leftLabel = new Label(shell, SWT.BORDER | SWT.WRAP);
		leftLabel.setText("上のLabelの\n5ピクセル下に\nLabelを配置\nします。");
		FormData leftForm = new FormData();
		leftForm.top = new FormAttachment(topLabel, 5);
		leftForm.left = new FormAttachment(0, 1);
		leftForm.bottom = new FormAttachment(100, -1);
		leftLabel.setLayoutData(leftForm);
		// 右下にButtonを追加します
		Button rightButton = new Button(shell, SWT.PUSH);
		rightButton.setText("右下端にButtonを配置します");
		FormData bottomRightForm = new FormData();
		bottomRightForm.bottom = new FormAttachment(100, -5);
		bottomRightForm.right = new FormAttachment(100, -5);
		rightButton.setLayoutData(bottomRightForm);
		// その隣にもButtonを追加します
		Button leftButton = new Button(shell, SWT.PUSH);
		leftButton.setText("隣にもう1つButtonを配置します");
		FormData bottomLeftForm = new FormData();
		bottomLeftForm.bottom = new FormAttachment(100, -5);
		bottomLeftForm.right = new FormAttachment(rightButton, -5);
		leftButton.setLayoutData(bottomLeftForm);
		// 最後にTextを追加します
		Text text = new Text(shell, SWT.BORDER);
		text.append("残りの領域にTextを配置します。");
		FormData centerForm = new FormData();
		centerForm.top = new FormAttachment(topLabel, 5);
		centerForm.left = new FormAttachment(leftLabel, 5);
		centerForm.bottom = new FormAttachment(rightButton, -5);
		centerForm.right = new FormAttachment(100, -5);
		text.setLayoutData(centerForm);
		// ShellオブジェクトをOpenします
		shell.open();
		while (shell != null && !shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
