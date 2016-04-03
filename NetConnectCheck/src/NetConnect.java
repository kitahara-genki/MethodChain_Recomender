import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class NetConnect {
	public static void main(String[] args) {
        String urlString = "http://localhost/net_examples/post.jsp";
        try {
        	ip();
            URL url = new URL(urlString);
            URLConnection uc = url.openConnection();
            uc.setDoOutput(true);//POST可能にする

            //--------------------------//
    		// post内容の設定と送信			//
    		//--------------------------//

            uc.setRequestProperty("User-Agent", "@IT java-tips URLConnection");// ヘッダを設定
            uc.setRequestProperty("Accept-Language", "ja");// ヘッダを設定
            OutputStream os = uc.getOutputStream();//POST用のOutputStreamを取得

            String postStr = "foo1=bar1&foo2=bar2";//POSTするデータ
            PrintStream ps = new PrintStream(os);
            ps.print(postStr);//データをPOSTする
            ps.close();

            //--------------------------//
    		// サーバから検索結果を取得		//
    		//--------------------------//

            InputStream is = uc.getInputStream();//POSTした結果を取得
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
            reader.close();
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + urlString);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Can't connect to " + urlString);
            System.exit(-1);
        }
    }
	
	public static void ip(){
		try{
            InetAddress local = InetAddress.getLocalHost();
            System.out.println(local);
            System.out.println("\t" + local.getHostName());
            System.out.println("\t" + local.getHostAddress());
        }
        catch(SecurityException se){
            System.err.println("セキュリティ例外です。");
        }
        catch(UnknownHostException ue){
            System.err.println("ローカルホストアドレスが取得できません");
            System.exit(1);
        }
	}
}
