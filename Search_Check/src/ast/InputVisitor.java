package ast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

/**
 * ASTからメソッドチェーンのデータを抽出
 * @author kitahara
 *
 */
public class InputVisitor extends ASTVisitor {
	HashMap<String, Integer>  map = new HashMap<String, Integer>();
	MethodChain mc;
	String pre_class;
	int pre_number;
	int chain_num;
	StringBuffer buffer;
	public static final int FIRST_CHAIN = 1, SECOND_CHAIN = 2, THIRD_CHAIN = 3;

	public InputVisitor(){
		super();
		pre_class = "";
		pre_number = 0;
		mc = new MethodChain();
		buffer = new StringBuffer();
		chain_num = THIRD_CHAIN;
	}
	//メソッド宣言を抽出------------------------------------------------------------
	public boolean visit(MethodInvocation node) {
		StringBuffer sb = new StringBuffer();
		// メソッドの詳細情報を取得
		IMethodBinding methodbind = node.resolveMethodBinding();
	    if (methodbind == null) return false;

	    //ここがうまくできてない → 使う関数が違うだけだった。できた
	    ITypeBinding[] parameters = methodbind.getParameterTypes();

	    //ITypeBinding[] → String[]に
	    String[] params = new String[parameters.length+1];
	    for (int i = 0; i < parameters.length; i++) {params[i] = parameters[i].getName();}

	    ITypeBinding tb = node.resolveTypeBinding();

		//---------------------------------------------------------------------
		// 本番。情報を受け取って解析データを作成
		//---------------------------------------------------------------------
		String now_class = methodbind.getDeclaringClass().getName();
		String allname = "";
		int now_number = node.getStartPosition();
		if(now_class.equals(pre_class) && now_number == pre_number){
			if(chain_num == SECOND_CHAIN){
				mc.setReceive2(methodbind.getReturnType().getName());
				mc.setParm2(params);
				mc.setMethodname2(node.getName().toString());

				chain_num = FIRST_CHAIN;
				Expression s = node.getExpression();
				if(s.toString().equals(s.resolveTypeBinding().getName())){//first_methodが関数でないとき
					mc.setReceive1(s.toString());
					allname = mc.getAllname();
					buffer.append(" " + allname);
					if(map.containsKey(allname)) map.put(allname, map.get(allname)+1);
					else map.put(allname, 1);
				}
			}
			else if(chain_num == FIRST_CHAIN){
				mc.setReceive1(methodbind.getReturnType().getName());
				mc.setParm1(params);
				chain_num = FIRST_CHAIN;
				allname = mc.getAllname();
				buffer.append(" " + allname);
				if(map.containsKey(allname)) map.put(allname, map.get(allname)+1);
				else map.put(allname, 1);
			}
		}
		else if(node.getExpression() != null){//そのチェーンを初めて見た時で長さが2以上
			mc = new MethodChain();
			mc.setReceive3(methodbind.getReturnType().getName());
			mc.setParm3(params);
			mc.setMethodname3(node.getName().toString());
			mc.setAssignment(node.getParent().toString());
			Expression s = node.getExpression();
			//System.out.println(s);
			if(s != null){
				int type = s.getNodeType();
				if(type == ASTNode.SIMPLE_NAME){//ひとつ前の呼び出しがローカル変数の時

					mc.setReceive1(s.resolveTypeBinding().getName());
					allname = mc.getAllname();
					buffer.append(" " + allname);
					if(map.containsKey(allname)) map.put(allname, map.get(allname)+1);
					else map.put(allname, 1);
					chain_num = THIRD_CHAIN;
				}
				else if(type == ASTNode.QUALIFIED_NAME){//オブジェクト.フィールドの時
					mc.setReceive2(now_class);
					chain_num = SECOND_CHAIN;
				}
				else{
					chain_num = SECOND_CHAIN;
				}
			}
		}
		pre_class = now_class;
		pre_number = now_number;

		//---------------------------------------------------------------------

		return super.visit(node);
	}
	//-------------------------------------------------------------------------

	//QualifiedName(フィールドアクセス)------------------------------------------------------------
	public boolean visit(QualifiedName node) {
		if(chain_num == SECOND_CHAIN){
			mc.setReceive1(node.getQualifier().toString());
			mc.setMethodname2(node.getName().toString());
			String allname = mc.getAllname();
			buffer.append(" " + allname);
			if(map.containsKey(allname)) map.put(allname, map.get(allname)+1);
			else map.put(allname, 1);
			chain_num = THIRD_CHAIN;
		}
		else chain_num = THIRD_CHAIN;
		return super.visit(node);
	}

	//-------------------------------------------------------------------------
	/**
	 * ファイルに、1コードごとのメソッドチェーンの出現を記録
	 */
	public void writeFile(){
		File file = new File("input.txt");
		StringBuffer sb = new StringBuffer();
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter
					(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()){
			      String o =  it.next();
			      //System.out.println(o+":"+map.get(o)+"回");
			      sb.append("[&quot;" + o + "&quot;," + map.get(o) + "],");
			}
			int index = sb.lastIndexOf(",");
			sb.deleteCharAt(index);			// 最後の,を削除
			System.out.println(System.getProperty("user.dir") + "input.txt\n(InputVisitor)input.txtの確認\n" + sb);			// 確認
			pw.println(sb);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
