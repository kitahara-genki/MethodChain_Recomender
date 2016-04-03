import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;


public class ASTTest {

	public static void main(String[] args) {
		try {
			// 次の setResolveBindings と setEnvironment が重要！！
	        // setResolveBindings(true) をしておかないとまともに解析はできない．
	        // setResolveBindings をまともに機能させるために setEnvironment が必要．
	        // setEnvironment の第一引数にはクラスパスの配列．第二引数にはソースコードを検索するパスの配列
	     	// 第三第四については何も考えず null, true ．納得いかない時はIBMのASTPa...

			//-----------------------------
			// 下準備
			//-----------------------------
			ASTParser parser;
			//MyVisitor_gomi visitor = new MyVisitor_gomi();
			MyVisitor visitor = new MyVisitor();
			int id = 8343;
			visitor.writeInit();

			//-----------------------------
			// ファイルを探し、順番に解析
			//-----------------------------
			String path = System.getProperty("user.dir") + "\\libs_test2";
			//String path = "C:\\libs_t2";
		    File dir = new File(path);
		    System.out.println(path);
		    File[] filestr = dir.listFiles();//arg[0]フォルダ内のファイルを検索
		    for (File fs : filestr) {
				System.out.println("Reading " + fs + " . . .");
		    	//--------------------------
				//ファイルの中身を文字列に変換
				//--------------------------
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(fs)));
				String line = "";
				while( (line = br.readLine()) != null ){
					sb.append(line+"\n");
				}
				parser = ASTParser.newParser(AST.JLS8);
				parser.setBindingsRecovery(true);
		        parser.setStatementsRecovery(true);
				parser.setResolveBindings(true);
				parser.setEnvironment(null, null, null, true);
				parser.setUnitName("Test");// なんでもいいから名前を設定しておく
				//visitor = new MyVisitor_gomi();
				visitor = new MyVisitor();
				parser.setSource(sb.toString().toCharArray());//ソースファイルをパーサにセット
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());//ASTの作成、unitは
				// 今までのやり方
				/*visitor.writeId(fs, id);//idの記述
				unit.accept(visitor);
				visitor.writeArticle();
				id++;*/
				
				// これからのやり方
				unit.accept(visitor);
				
				
				id = visitor.writeFile(fs, id);//idの記述
				
				//visitor.writeArticle();
				//id++;
				System.out.println("Done !\n----------------------------------------------\nnext "+id);
				br.close();
			}

		} catch ( FileNotFoundException e) {
			System.out.println("File not found." + e);
			return;
		} catch ( IOException e ){
			System.out.println("IO Exception !");
			return;
		}
	}

}
