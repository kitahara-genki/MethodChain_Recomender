package ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;
/**
 * ネットワークを利用して検索を行う
 * @author kitahara
 *
 */
public class GETAssocSearcher {
	ArrayList<String[]> output = new ArrayList<String[]>();
	HashSet<String> checkset = new HashSet<String>();
	public ArrayList<String[]> search(String data){
		HttpURLConnection huc = null;
        BufferedReader response;
		PrintWriter request;
		Pattern pattern1 = Pattern.compile("\""), 	// 検索結果の文字列を「"」を境に分割
		pattern2 = Pattern.compile("~"); 			// データ文字列を「~」を境に分割
		try {

			URL url = new URL("http://192.168.65.130/getassoc/gss3");
			huc = (HttpURLConnection) url.openConnection();
			huc.setDoOutput(true);
			huc.setRequestMethod("POST");

			//------------------//
			//	ヘッダの設定		//
			//------------------//

			huc.setRequestProperty("Content-Type","text/xml;charset=utf-8");

			//------------------//
			//	リクエスト内容(xml)	//
			//------------------//

			request = new PrintWriter(huc.getOutputStream());
			request.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
					"\r\n<gss version=\"3.0\"><assoc target=\"javatest\" niwords=\"70\" "
					+ "cutoff-df=\"0\" stage1-sim=\"WT_SMARTAW\" narticles=\"10\" nkeywords=\"10\""
					+ " yykn=\"10\" nacls=\"1\" nkcls=\"1\" a-offset=\"0\" a-props=\"title\" cross-ref=\"aw\" "
					+ "stage2-sim=\"WT_SMARTWA\"><article vec=\"["
					+ data + "]\"/></assoc></gss>");

			request.close();

			//------------------//
			//	レスポンス受信		//
			//------------------//

			response = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String line;
			String search = "keyword name";
			while ((line = response.readLine()) != null) {
				//System.out.println(line); // レスポンスを表示
				if(line.indexOf(search) != -1){	// keyword name を含む行なら

					//------------------//
					// データをoutputに格納	//
					//------------------//

					String[] methodchain = pattern1.split(line, 0); // "を境に分割,チェーン自体は2つ目に保存

					methodchain[1] = methodchain[1].replaceAll("\\(", "<"); //	(を<に変更
					methodchain[1] = methodchain[1].replaceAll("\\)", ">"); //	)を>に変更


					if(checkset.isEmpty()){
						checkset.add(methodchain[1]);
						String[] ss = pattern2.split(methodchain[1], 0); // 「~」で分割
						output.add(ss);
						for(String s:ss){
							System.out.print(s + " ");
						}
						System.out.println();
					}

					else{
						if(!checkset.contains(methodchain[1])){// 重複していないならば
							checkset.add(methodchain[1]);
							String[] ss = pattern2.split(methodchain[1], 0); // 「~」で分割
							output.add(ss);
							for(String s:ss){
								System.out.print(s + " ");
							}
							System.out.println();
						}
					}
					/*for (int i = 0; i < output.size(); i++) { // outputにちゃんと保存されたことを確認
						for (String string : output.get(i)) {
							System.out.print(string + " ");
						}
						System.out.println(i);
					}
					System.out.println();*/



				}
			}
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (huc != null) {
				System.out.println("切断されました : ");
				huc.disconnect();
			}
		}
		return output;
	}

	public void refine(){

	}

}
