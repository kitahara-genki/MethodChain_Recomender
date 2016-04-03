import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ast.ASTCheck;
import ast.GETAssocSearcher;


public class check {

	public static void main(String[] args) {

		String path = System.getProperty("user.dir") + "\\now_code\\Test.java";
		File now_file = new File(path);
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {

			//------------------------------//
			// 記述中のコードから検索用データの作成	//
			//------------------------------//

			br = new BufferedReader(new FileReader(now_file));
			String line = "";
			while( (line = br.readLine()) != null ){// コードを読み込んでString型のsbに保存
				sb.append(line+"\n");
			}
			br.close();

			ASTCheck.makeData(sb.toString()); // ASTの作成,input.txtにデータが記述される

			//------------------------------//
			// input.txtの読み込み			//
			//------------------------------//

			path = System.getProperty("user.dir") + "\\input.txt";// input.txtにパスをつなげる
			now_file = new File(path);
			sb = new StringBuffer();

			br = new BufferedReader(new FileReader(now_file));
			while( (line = br.readLine()) != null ){//ファイルを1行ずつsbに読み込む(input.txtは1行しかないが)
				sb.append(line+"\n");
			}
			br.close();

			//------------------------------//
			// 検索							//
			//------------------------------//

			GETAssocSearcher geta = new GETAssocSearcher();
			ArrayList<String[]> candidate = geta.search(sb.toString());
			System.out.println(candidate.size());

			/*for (String[] strings : candidate) {
				System.out.println(strings[5]); //返り値の型を表示
			}*/

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
