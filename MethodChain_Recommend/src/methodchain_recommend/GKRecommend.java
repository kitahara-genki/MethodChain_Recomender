package methodchain_recommend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.ui.text.java.JavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationExtension;
import org.eclipse.swt.graphics.Image;


@SuppressWarnings("restriction")
public class GKRecommend extends JavaCompletionProposalComputer {

	String path;
	File now_file;
	StringBuffer sb = new StringBuffer();
	BufferedReader br;
	Pattern space = Pattern.compile(" ");
	Pattern colon = Pattern.compile(" : ");
	Pattern ten = Pattern.compile("･");
	//Pattern kakko = Pattern.compile("(");
	static long startTime, existTime, astTime, searchTime, endTime;

	public GKRecommend(){
		super();
	}

	//********************************************************************************************

	//------------------------------//
	// 既存のコードのコピー				//
	//------------------------------//

	protected CompletionProposalCollector createCollector(
			JavaContentAssistInvocationContext context) {
		CompletionProposalCollector collector = super.createCollector(context);
		collector.setIgnored(13, false);
		collector.setIgnored(1, false);
		collector.setIgnored(27, false);
		collector.setIgnored(2, false);
		collector.setIgnored(25, false);
		collector.setIgnored(3, false);
		collector.setIgnored(4, false);
		collector.setIgnored(5, false);
		collector.setIgnored(7, false);
		collector.setIgnored(12, false);
		collector.setIgnored(6, false);
		collector.setIgnored(26, false);
		collector.setIgnored(24, false);
		collector.setIgnored(8, false);
		collector.setIgnored(11, false);
		collector.setIgnored(10, false);
		return collector;
	}

	protected int guessContextInformationPosition(
			ContentAssistInvocationContext context) {
		return guessMethodContextInformationPosition(context);
	}

	private static final class ContextInformationWrapper implements
	IContextInformation, IContextInformationExtension {
		private final IContextInformation fContextInformation;
		private int fPosition;

		public ContextInformationWrapper(IContextInformation contextInformation) {
			this.fContextInformation = contextInformation;
		}

		public String getContextDisplayString() {
			return this.fContextInformation.getContextDisplayString();
		}

		public Image getImage() {
			return this.fContextInformation.getImage();
		}

		public String getInformationDisplayString() {
			return this.fContextInformation.getInformationDisplayString();
		}

		public int getContextInformationPosition() {
			return this.fPosition;
		}

		public void setContextInformationPosition(int position) {
			this.fPosition = position;
		}

		public boolean equals(Object object) {
			if ((object instanceof ContextInformationWrapper)) {
				return this.fContextInformation
						.equals(((ContextInformationWrapper) object).fContextInformation);
			}
			return this.fContextInformation.equals(object);
		}

		public int hashCode() {
			return this.fContextInformation.hashCode();
		}
	}
	//************************************************************************************************
	@Override
	public List<ICompletionProposal> computeCompletionProposals(
			ContentAssistInvocationContext context, IProgressMonitor arg1) {
		startTime = System.currentTimeMillis();
		if (!(context instanceof JavaContentAssistInvocationContext)) {
			System.out.println("asdfasdfasdfa");
			return Collections.emptyList();
		}

		JavaContentAssistInvocationContext javaContext = (JavaContentAssistInvocationContext) context;


		//------------------------------//
		// 既存の補完候補を取得				//
		//------------------------------//

		List<ICompletionProposal> existlist = super.computeCompletionProposals(context, arg1);// 第1チェーンの候補リスト
		existTime = System.currentTimeMillis();
		/*System.out.println("existlist-----------------------------");
		for (ICompletionProposal ic : existlist) {
			System.out.println(ic.getDisplayString());
		}*/
		System.out.println("--------------------------------------");
		IType expected_class = javaContext.getExpectedType();//オブジェクト型の時
		char[][] completion_context = javaContext.getCoreContext().getExpectedTypesKeys();
		String expected_class_string = "";

		if(expected_class != null){// オブジェクト型の時
			System.out.println(expected_class);
			expected_class_string = space.split(expected_class.toString(), 0)[1];
		}
		else if(completion_context != null){// プリミティブ型の時
			switch(completion_context[0][0]){
			case 'I':
				expected_class_string = "int";
				break;
			case 'D':
				expected_class_string = "double";
				break;
			case 'F':
				expected_class_string = "float";
				break;
			case 'S':
				expected_class_string = "short";
				break;
			case 'J':
				expected_class_string = "long";
				break;
			case 'C':
				expected_class_string = "char";
				break;
			case 'Z':
				expected_class_string = "boolean";
				break;
			case 'B':
				expected_class_string = "byte";
				break;
			default :
				break;
			}
		}

		else {// void型の時
			expected_class_string = "void";
		}
		System.out.println(expected_class_string);

		//------------------------------//
		// 記述中のコードから検索用データの作成	//
		//------------------------------//

		String text = javaContext.getDocument().get();//string型でソースコードを取得
		//System.out.println(text);
		ASTCheck.makeData(text);// ASTの作成とinput.txtを作成
		astTime = System.currentTimeMillis();

		//------------------------------//
		// 検索用のデータを取り出し			//
		//------------------------------//

		try{
			path = System.getProperty("user.dir") + "\\input.txt";
			now_file = new File(path); // 検索用データファイルの作成
			sb = new StringBuffer();
			String line = "";
			br = new BufferedReader(new FileReader(now_file));
			while( (line = br.readLine()) != null ){//ファイルをString型で保存
				sb.append(line+"\n");
			}

			//------------------------------//
			// GETAssocで検索					//
			//------------------------------//

			GETAssocSearcher geta = new GETAssocSearcher();
			ArrayList<String[]> candidate_chain = geta.search(sb.toString()); // candidate は検索結果
			searchTime = System.currentTimeMillis();
			int start = javaContext.getInvocationOffset(); // 現在のカーソルの位置
			int length = 5;

			//------------------------------//
			// 絞り込み						//
			//------------------------------//



			List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
			for (String[] chain : candidate_chain) {
				String chain1 = chain[1];
				//String chainname2 = chain[5];
				//String chain3 = chain[6];
				String chainname2 = chain[4];
				String chain3 = chain[5];
				//----------------------------------------------//
				// 求められている型が存在しない場合						//
				//----------------------------------------------//
				if(expected_class_string.equals("void")){
					if(chain[0].equals("s")){						// staticなら
						String cs;
						if(chainname2.equals(""))cs = chain1+"."+chain[7];//1.3()
						else {//第2チェーンが存在するとき
							cs = chain1+"."+chainname2;//1.2
							if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
								cs = setParams(cs,chain[3]) + "." + chain[7];//1.2().3
							else cs = cs + "." + chain[7];//1.2.3

						}
						if(!chain[6].equals("[･]"))//第3チェーンがメソッドの時
							cs = setParams(cs,chain[6]);

						CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
						list.add(proposal);
					}
					else if(chain[0].equals("n")){					// newなら
						String cs;
						if(chainname2.equals(""))cs = "new "+chain1+"."+chain[7];
						else {//第2チェーンが存在するとき
							cs =  "new "+chain1+"."+chainname2;//+"."+chain[8];
							if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
								cs = setParams(cs,chain[3]) + "." + chain[7];
							else cs = cs + "." +  chain[7];
						}
						if(!chain[7].equals("[･]"))//第3チェーンがメソッドの時
							cs = setParams(cs,chain[6]);

						CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
						list.add(proposal);
					}
					else {
						for(int i = 0; i < existlist.size(); i++){
							ICompletionProposal cp = existlist.get(i);
							String scp = cp.getDisplayString();
							String[] exist_str;
							String[] exist_str2;
							//System.out.println("exist\t\t"+scp + ", " + cp.getContextInformation());
							if(scp.indexOf(" : ") != -1){// true false以外の時
								exist_str = colon.split(scp, 0);
								exist_str2 = space.split(exist_str[1], 0);
								if(chain1.equals("")){//第１チェーンが無い時
									String exist_str3 = exist_str[0].split("\\(")[0];
									if(chainname2.equals(exist_str3)){			// 第2チェーンと文字列が一致したら候補に入れる
										String cs = exist_str[0];

										if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
											cs = setParams(cs,chain[3]) + "." + chain[7];
										else cs = cs + "." +  chain[7];								
										if(!chain[6].equals("[･]"))cs = setParams(cs,chain[6]);//第3チェーンがメソッドの時
										CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
										list.add(proposal);
									}
								}
								else{
									if(chain1.equals(exist_str2[0])){			// 第1チェーンの型が一致したら候補に入れる
										String cs;
										if(chainname2.equals(""))cs = exist_str[0]+"."+chain[7];
										else {//第2チェーンが存在するとき
											cs = exist_str[0]+"."+chainname2;//+"."+chain[8];
											if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
												cs = setParams(cs,chain[3]) + "." + chain[7];
											else cs = cs + "." +  chain[7];
										}
										if(!chain[6].equals("[･]"))//第3チェーンがメソッドの時
											cs = setParams(cs,chain[6]);

										CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
										list.add(proposal);
									}
								}
							}
						}
					}
				}
				//----------------------------------------------//
				// 求められている型が存在する場合						//
				//----------------------------------------------//
				else {
					if(chain3.equals(expected_class_string)){			// 第3チェーンの型と求められている型が一致したら
						if(chain[0].equals("s")){						// staticメソッドならchain3のチェックだけでよい
							String cs;
							if(chainname2.equals("")){cs = chain1+"."+chain[7];
							//System.out.println("method_chain" + cs);
							}
							else {//第2チェーンが存在するとき
								cs = chain1+"."+chainname2;//+"."+chain[8];
								if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
									cs = setParams(cs,chain[3]) + "." + chain[7];
								else cs = cs + "." + chain[7];
							}
							if(!chain[6].equals("[･]")){//第3チェーンがメソッドの時
								cs = setParams(cs,chain[6]);

							}

							CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
							list.add(proposal);
						}
						else if(chain[0].equals("n")){						// newならchain3のチェックだけでよい
							String cs;
							if(chainname2.equals(""))cs = "new "+chain1+"."+chain[7];
							else {//第2チェーンが存在するとき
								cs =  "new "+chain1+"."+chainname2;//+"."+chain[8];
								if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
									cs = setParams(cs,chain[3]) + "." + chain[7];
								else cs = cs + "." +  chain[7];
							}
							if(!chain[6].equals("[･]"))//第3チェーンがメソッドの時
								cs = setParams(cs,chain[6]);
							CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
							list.add(proposal);
						}
						else {
							for(int i = 0; i < existlist.size(); i++){
								ICompletionProposal cp = existlist.get(i);
								String scp = cp.getDisplayString();
								String[] exist_str;
								String[] exist_str2;
								//System.out.println("exist\t\t"+scp + ", " + cp.getContextInformation());
								if(scp.indexOf(" : ") != -1){// true false以外の時
									exist_str = colon.split(scp, 0);
									exist_str2 = space.split(exist_str[1], 0);
									//System.out.println("exist_Str2 = "+exist_str2[0] + ",chain1 = " + chain1);
									if(chain1.equals("")){//第１チェーンが無い時
										String exist_str3 = exist_str[0].split("\\(")[0];
										if(chainname2.equals(exist_str3)){			// 第2チェーンと文字列が一致したら候補に入れる
											String cs = exist_str[0];

											if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
												cs = setParams(cs,chain[3]) + "." + chain[7];
											else cs = cs + "." +  chain[7];								
											if(!chain[6].equals("[･]"))cs = setParams(cs,chain[6]);//第3チェーンがメソッドの時
											CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
											list.add(proposal);
										}
									}
									else{
										if(chain1.equals(exist_str2[0])){			// 第1チェーンの型が一致したら候補に入れる
											String cs;
											if(chainname2.equals(""))cs = exist_str[0]+"."+chain[7];
											//	System.out.println("method_chain" + cs);

											else {//第2チェーンが存在するとき
												cs = exist_str[0]+"."+chainname2;//+"."+chain[8];
												if(!chain[3].equals("[･]"))//第2チェーンがメソッドの時
													cs = setParams(cs,chain[3]) + "." + chain[7];
												else cs = cs + "." +  chain[7];
											}
											if(!chain[6].equals("[･]"))cs = setParams(cs,chain[6]);//第3チェーンがメソッドの時
											CompletionProposal proposal = new CompletionProposal(cs, start, 0, cs.length());
											list.add(proposal);
										}
									}
								}
							}
						}
					}
				}



			/*	String s1 = "";
				for (String string : s) {//候補の文字列を足していく
					s1 += string;
				}
				CompletionProposal proposal = new CompletionProposal(s1, start, 0, s1.length());
				list.add(proposal);*/
			}
			//list.add(new CompletionProposal("aaaaaaaaa", start, 0, 9));
			//list.add(new CompletionProposal("#sumo", start, 0, 5));
			br.close();
			endTime = System.currentTimeMillis();
			writeTime(list.size());
			//System.out.println("実行時間\t" + (endTime - startTime));
			//System.out.println("既存の候補\t" + (secondTime - startTime));
			return list;

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * メソッドチェーンsと引数群paramsをもらってs(p1,p2,…)という形にして返す
	 * @param s
	 * @param params
	 * @return
	 */
	private String setParams(String s, String params){
		String param2 = params.substring(1, params.length()-1);
		String[] params2 = ten.split(param2, 0);
		s=s+"(";
		//System.out.println("method_chain1\t" + s);
		String sp="";
		for(int i=0;i<params2.length-1;i++){
			sp=sp+params2[i]+",";
		}
		if(sp.length()>0)sp=sp.substring(0, sp.length()-1);
		s = s+sp  + ")";
		//System.out.println("method_chain2\t" + s + "\tsp\t" + sp);
		return s;
	}

	private void writeTime(int size){
		File file = new File("time_jikken4_StdIn.csv");
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter
					(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8")));
			//.println("既存,AST解析,検索,絞り込み,合計");
			pw.println((existTime - startTime) + "," + (astTime - existTime) + "," + (searchTime - astTime) + "," +
					(endTime - searchTime) + "," + (endTime - startTime) + ",," + (size));
			// ---------------------------------------
			pw.close();

			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext arg0, IProgressMonitor arg1) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
/*
	@Override
	public String getErrorMessage() {
		//System.out.println("getErrorMessage");
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void sessionEnded() {
		//System.out.println("sessionEnded");
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void sessionStarted() {
		//System.out.println("sessionStarted");
		// TODO 自動生成されたメソッド・スタブ

	}*/

}
