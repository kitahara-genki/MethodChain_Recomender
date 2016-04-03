package ast;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;


public class ASTCheck {

	static CompilationUnit unit;
	/**
	 * 検索前にASTを作成
	 * @param s
	 */
	public static void makeData(String s) {

		//--------------------------//
		// 下準備					//
		//--------------------------//

		ASTParser parser;
		InputVisitor visitor = new InputVisitor();
		parser = ASTParser.newParser(AST.JLS8);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setResolveBindings(true);
		parser.setEnvironment(null, null, null, true);
		parser.setUnitName("Test");// なんでもいいから名前を設定しておく
		visitor = new InputVisitor();
		parser.setSource(s.toCharArray());//ソースファイルをパーサにセット
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());//ASTの作成、unitは

		//-----------------------------
		// 解析
		//-----------------------------

		unit.accept(visitor);
		visitor.writeFile();// ファイルに書き込み
		System.out.println("ASTCheck Done !\n----------------------------------------------");
	}

}
