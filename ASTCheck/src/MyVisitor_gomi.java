import java.io.File;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;


public class MyVisitor_gomi extends ASTVisitor {
	HashMap<String,String> map = new HashMap<String,String>();
	HashMap<String, Integer>  chain_map = new HashMap<String, Integer>();
	MethodChain mc;
	String pre_class;
	int pre_number;
	int chain_num;
	StringBuffer buffer;
	int id;
	public static final int FIRST_CHAIN = 1, SECOND_CHAIN = 2, THIRD_CHAIN = 3;

	public MyVisitor_gomi(){
		super();
		pre_class = "";
		pre_number = 0;
		mc = new MethodChain();
		buffer = new StringBuffer();
		id = 1;
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

	    
	    // レシーバの型
	    sb.append("レシーバの型\t\t" + methodbind.getReturnType().getName() + "\n");

	    // 引数の型
	    if(parameters.length==0)System.out.println("要素なし");
	    else {
	    	sb.append("引数の型\t\t[");
		    for (ITypeBinding arg : parameters) {
		    	sb.append(arg.getName() + ", ");
			}
		    sb.append("]\n");
	    }


		// メソッド名
		sb.append("メソッド名\t\t" + node.getName().toString() + "\n");

		// 引数
		sb.append("引数\t\t" + node.arguments() + " \n");

		// そのメソッドの前の呼び出し
		//MethodInvocation n = (MethodInvocation)node.getExpression();

		//if(tb != null)sb.append("クラス\t\t\t" + tb.getName() + " \n");
		if(node.getExpression()!= null){
			// スタティックかどうか
			sb.append("前\t\t" + node.getExpression().toString() + " \n");
			sb.append("前の型\t\t" + node.getExpression().resolveTypeBinding().getName() + " \n***********\n");
			if(node.getExpression().toString().equals(node.getExpression().resolveTypeBinding().getName()))
				System.out.println("Saaaaaaaaaaaaaaaaaaaaaa");
			sb.append("前について\t\t" + node.getExpression().getNodeType() + " \n");
			sb.append("クラスか\t\t" + node.getExpression().resolveTypeBinding().isClass()+ " \n");
		}
		else sb.append("前\t\tなし \n");
		// 式の右辺全体
		
		sb.append("ここ！！\t\t" + node.getStartPosition() + " \n");
		sb.append("式全体\t\t" + node.getParent() + "\n----------------------------------------------\n");
		System.out.println(sb);

		//---------------------------------------------------------------------
		// 本番。情報を受け取って解析データを作成
		//---------------------------------------------------------------------
		String now_class = methodbind.getDeclaringClass().getName();
		int now_number = node.getStartPosition();
		//System.out.println("pre = " + pre_class + ", now = " + now_class);
		//System.out.println("pre = " + pre_number + ", now = " + now_number);
		if(now_class.equals(pre_class) && now_number == pre_number){
			if(chain_num == SECOND_CHAIN){
				mc.setReceive2(methodbind.getReturnType().getName());
				mc.setParm2(params);

				chain_num = FIRST_CHAIN;
				Expression s = node.getExpression();
				if(s.toString().equals(s.resolveTypeBinding().getName())){//first_methodが関数でないとき
					//mc.setFirstclass(s.resolveTypeBinding().getName());
					mc.setReceive1(s.toString());
					//System.out.println(mc.getAllname());
					buffer.append(" " + mc.getAllname());
				}
				//System.out.println("SecondNAME\t\t" + mc.getAllname());
			}
			else if(chain_num == FIRST_CHAIN){
				mc.setReceive1(methodbind.getReturnType().getName());
				mc.setParm1(params);
				//mc.setFirstclass(now_class);
				chain_num = FIRST_CHAIN;
				//System.out.println("ALLNAME\t\t" + mc.getAllname());
				buffer.append(" " + mc.getAllname());
			}
		}
		else {//そのチェーンを初めて見た時
			mc = new MethodChain();
			Expression s = node.getExpression();
			if(s != null){ // 長さが2以上なら
				mc.setReceive3(methodbind.getReturnType().getName());
				mc.setParm3(params);
				mc.setMethodname3(node.getName().toString());
				mc.setAssignment(node.getParent().toString());
				int type = s.getNodeType();
				if(type == ASTNode.SIMPLE_NAME){//ひとつ前の呼び出しがローカル変数やクラス名の時
				//	mc.setFirstclass(s.resolveTypeBinding().getName());
					//mc.setReceive1(s.resolveTypeBinding().getName());
					//System.out.println("FirstNAME\t\t" + mc.getAllname());
				//	System.out.println(mc.getAllname());
				//	buffer.append(" " + mc.getAllname());
					System.out.println("32isDeclaration\t" + ((SimpleName) s).isDeclaration() + ", " + ((SimpleName) s).getFullyQualifiedName());
					chain_num = SECOND_CHAIN;
				}
				else if(type == ASTNode.QUALIFIED_NAME){//オブジェクト.フィールドの時
				//	mc.setReceive2(now_class);
					System.out.println(now_class);
					//mc.setReceive1();
					chain_num = SECOND_CHAIN;
				}
				else if(type == ASTNode.CLASS_INSTANCE_CREATION){//new演算子の時
					//	mc.setReceive2(now_class);
						System.out.println(now_class);
						//mc.setReceive1();
						chain_num = SECOND_CHAIN;
					}
				else{
				//	System.out.println("ThirdNAME\t\t" + mc.getAllname());
					chain_num = SECOND_CHAIN;
				}
			}


		}


		//---------------------------------------------------------------------

		//System.out.println(sb);
		return super.visit(node);
	}
	//-------------------------------------------------------------------------

	//QualifiedName(フィールドアクセス)40------------------------------------------------------------
	public boolean visit(QualifiedName node) {
		if(chain_num == SECOND_CHAIN){
			mc.setReceive1(node.getQualifier().toString());
			System.out.println("first\t\t" + node.getQualifier().toString());
			System.out.println("firstの番号\t\t" + node.getQualifier().getNodeType());
				System.out.println("second\t\t" + node.getName());
				

			buffer.append(" " + mc.getAllname()+ "\r\n");
			chain_num = FIRST_CHAIN;// chainはつながっている
		}
		else chain_num = THIRD_CHAIN;// chainやりなおし
		return super.visit(node);
	}

	//****************************************************************************************

	//SimpleName(ローカル変数など)42------------------------------------------------------------
		public boolean visit(SimpleName node) {
			
			if(chain_num == SECOND_CHAIN){
				System.out.println("^^^^^^\n42isDeclaration\t" + node.isDeclaration() + ", " + node.getFullyQualifiedName());
				mc.setReceive2(node.getFullyQualifiedName());
					System.out.println("first\t\t" + node.getFullyQualifiedName());
					if(node.getParent()!= null)System.out.println("first1の前\t\t"+node.getParent());
				buffer.append(" " + mc.getAllname()+ "\r\n");
				chain_num = FIRST_CHAIN;// chainはつながっている
			}
			else chain_num = THIRD_CHAIN;// chainやりなおし
			return super.visit(node);
		}

	//****************************************************************************************

		public boolean visit(ClassInstanceCreation node) {
			System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[Classinstance\t");
			System.out.println(node.getType());
			System.out.println(node.toString());
			IMethodBinding methodbind = node.resolveConstructorBinding();
			if(methodbind != null){
				ITypeBinding[] tb = methodbind.getParameterTypes();
				for (ITypeBinding t : tb) {
					System.out.print(t.getName() + ", ");
				}
			}
			
			System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]\n");
			return super.visit(node);
		}	
		
		
	//FieldDeclaration(ローカル変数など)------------------------------------------------------------
/*	public boolean visit(FieldDeclaration node) {
		List l = node.fragments();
		System.out.println("----------------------------------------------\nFieldDeclaration\t");
		for(int i=0,j=l.size();i<j;i++)
			System.out.println(l.get(i).toString());
		System.out.println("----------------------------------------------\n");
		return super.visit(node);
	}*/

	//****************************************************************************************		

	//SimpleName(ローカル変数など)42------------------------------------------------------------
	/*public boolean visit(Initializer node) {

		System.out.println("Initializer\t\t" + node.getParent());

		return super.visit(node);
	}*/

//****************************************************************************************	
	
/*	public boolean visit(Operator node){
		
		System.out.println("Operator\t\t" + node.toString());
		return true;
		
	}*/
	
	public void writeInit(){}

	public void writeId(File f, int id){}

	public void writeArticle(){}

	public int writeFile(File f, int id){return id+1;}
}
