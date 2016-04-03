package methodchain_recommend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;


public class InputVisitor extends ASTVisitor {
	HashMap<String, Integer>  map = new HashMap<String, Integer>();
	Pattern kagikakko = Pattern.compile("<");
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
	    String[] params = makeParams(parameters);

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
				//mc.setAttr("m");
				putMap(mc.getAllname());

				chain_num = FIRST_CHAIN;
				Expression s = node.getExpression();
				if(s!=null){
					int type = s.getNodeType();
					if(type == ASTNode.SIMPLE_NAME){//ひとつ前の呼び出しがローカル変数の時（終わる）
						mc.setReceive1(s.resolveTypeBinding().getName());
						if(s.toString().equals(s.resolveTypeBinding().getName())){// staticなら
							mc.setAttr("s");
						}
						//else mc.setAttr("f");
						putMap(mc.getAllname());
						chain_num = THIRD_CHAIN;
					}
					else if(type == ASTNode.QUALIFIED_NAME){//オブジェクト.フィールドの時
						chain_num = FIRST_CHAIN;
					}
					else if(type == ASTNode.CLASS_INSTANCE_CREATION){//new演算子の時(終わる)
						chain_num = FIRST_CHAIN;
					}
					else{
						chain_num = FIRST_CHAIN;
					}
					/*if(s.toString().equals(s.resolveTypeBinding().getName())){//first_methodがクラス名の時
						mc.setReceive1(s.toString());
						mc.setAttr("s");
						putMap(mc.getAllname());
					}*/
				}
				else chain_num = THIRD_CHAIN;
			}
			else if(chain_num == FIRST_CHAIN){
				mc.setReceive1(methodbind.getReturnType().getName());
				mc.setParm1(params);
				chain_num = THIRD_CHAIN;
				//mc.setAttr("m");
				putMap(mc.getAllname());
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
					if(s.toString().equals(s.resolveTypeBinding().getName())){// staticなら
						mc.setAttr("s");
					}
					//else mc.setAttr("f");
					putMap(mc.getAllname());
					chain_num = THIRD_CHAIN;
				}
				else if(type == ASTNode.QUALIFIED_NAME){//オブジェクト.フィールドの時
					mc.setReceive2(now_class);
					chain_num = SECOND_CHAIN;
				}
				else if(type == ASTNode.CLASS_INSTANCE_CREATION){//new演算子の時(終わる)
					chain_num = FIRST_CHAIN;
				}
				else{
					chain_num = SECOND_CHAIN;
				}
			}
		}
		pre_class = now_class;
		pre_number = now_number;

		return super.visit(node);
	}

	//QualifiedName(フィールドアクセス)40------------------------------------------------------------
	public boolean visit(QualifiedName node) {
		if(chain_num == SECOND_CHAIN){
			mc.setMethodname2(node.getName().toString());
			//mc.setAttr("f");
			putMap(mc.getAllname());
			mc.setReceive1(node.getQualifier().toString());
			mc.setAttr("s");// staticである
			putMap(mc.getAllname());
			chain_num = THIRD_CHAIN;
		}
		else chain_num = THIRD_CHAIN;
		return super.visit(node);
	}
	//ClassInstanceCreation(new演算子)------------------------------------------------------------

	public boolean visit(ClassInstanceCreation node) {
		if(chain_num == FIRST_CHAIN){
			mc.setReceive1(node.getType().toString());
			IMethodBinding methodbind = node.resolveConstructorBinding();
			String[] params;
			if(methodbind != null){
				ITypeBinding[] parameters = methodbind.getParameterTypes();
				params = makeParams(parameters);
			}
			else {
				params = new String[1];
				params[0] = "";
			}
			mc.setParm1(params);
			mc.setAttr("n");// new演算子である
			putMap(mc.getAllname());
			chain_num = THIRD_CHAIN;
		}
		else chain_num = THIRD_CHAIN;
		return super.visit(node);
	}

	//-------------------------------------------------------------------------
	/**
	 * 引数の<>を消す
	 */
	public String[] makeParams(ITypeBinding[] tb){
		String[] params = new String[tb.length+1];
		for (int i = 0; i < tb.length; i++) {
			params[i] = tb[i].getName();
			if(params[i].indexOf('<') != -1)params[i] = kagikakko.split(params[i],0)[0];
		}
		return params;

	}
	/**
	 * マップにメソッドチェーンの出現を記録
	 * @param name
	 */
	private void putMap(String name){
		if(map.containsKey(name)) map.put(name, map.get(name)+1);
		else map.put(name, 1);
	}
	/**
	/**
	 * ファイルに、1コードごとのメソッドチェーンの出現を記録
	 */
	public void writeFile(){
		File file = new File(System.getProperty("user.dir") + "\\input.txt");
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
			System.out.println(index);
			if(!map.isEmpty())sb.deleteCharAt(index);			// 最後の,を削除
			System.out.println("(InputVisitor)input.txtの確認\n" + sb);			// 確認
			System.out.println(file.getPath());
			pw.println(sb);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
