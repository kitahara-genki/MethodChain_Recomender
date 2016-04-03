package ast;
/**
 * 抽出されたメソッドチェーンの情報をまとめるためのクラス
 * @author kitahara
 *
 */
public class MethodChain {

	private String //firstclass,
					receive1, receive2, receive3, methodname3, methodname2, allname, assignment;
	private String[] parm1, parm2, parm3;

	public MethodChain(){
	//	firstclass = "";
		receive1 = "";
		receive2 = "";
		receive3 = "";
		methodname2 = "";
		methodname3 = "";
		allname = "";
		assignment = "";
		parm1 = new String[]{""};
		parm2 = new String[]{""};
		parm3 = new String[]{""};
		setAllname();
	}
//	public String getFirstclass() 	{return firstclass;}
	public String getReceive1()		{return receive1;}
	public String getReceive2() 	{return receive2;}
	public String getReceive3() 	{return receive3;}
	public String getMethodname2() 	{return methodname2;}
	public String getMethodname3() 	{return methodname3;}
	public String getAllname() 		{return allname;}
	public String getAssignment() 	{return assignment;}
	public String[] getParm1() 		{return parm1;}
	public String[] getParm2() 		{return parm2;}
	public String[] getParm3() 		{return parm3;}

/*	public void setFirstclass(String firstclass) {
		this.firstclass = firstclass;
		setAllname();
	}*/
	public void setReceive1(String receive1) {
		this.receive1 = receive1;
		setAllname();
	}
	public void setReceive2(String receive2) {
		this.receive2 = receive2;
		setAllname();
	}

	public void setReceive3(String receive3) {
		this.receive3 = receive3;
		setAllname();
	}
	public void setMethodname2(String methodname) {
		this.methodname2 = methodname;
		setAllname();
	}

	public void setMethodname3(String methodname) {
		this.methodname3 = methodname;
		setAllname();
	}

	private void setAllname() {
		StringBuffer sb = new StringBuffer();
		sb.append(receive1 + "~[");
		for (String s : parm1) {
			sb.append(s + "･");
		}
		sb.append("]~");
		sb.append(receive2 + "~[");
		for (String s : parm2) {
			sb.append(s + "･");
		}
		sb.append("]~");
		sb.append(methodname2 + "~");
		sb.append(receive3 + "~[");
		for (String s : parm3) {
			sb.append(s + "･");
		}
		sb.append("]~");
		sb.append(methodname3);
		allname = sb.toString();
		allname = allname.replaceAll("<", "\\(");
		allname = allname.replaceAll(">", "\\)");
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public void setParm1(String[] parm1) {
		this.parm1 = parm1;
		setAllname();
	}

	public void setParm2(String[] parm2) {
		this.parm2 = parm2;
		setAllname();
	}

	public void setParm3(String[] parm3) {
		this.parm3 = parm3;
		setAllname();
	}
}
