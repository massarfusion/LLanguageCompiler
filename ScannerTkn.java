package scanning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class symbol{
	int number;
	int type;
	char[] name;
}

class analysizer{
	static String keyWord[] = {
			"and","begin","bool","do","else","end","false","if"
			,"integer","not","or","program","real","then","true"
			,"var","while"};
	public boolean isLetter(char a) {
		if (a>='a'&&a<='z'||a<='Z'&&a>='A') {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isDigit(char a) {
		if (a>='0'&&a<='9') {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isKwd(String s) {
		for (String kwd:keyWord) {
			if (kwd.equals(s)) {
				return true;
			}
		}
		return false;
	}
	public List<Map> generateSymbol(List<Token> ml,List<Token> easy) {
		List<Map> result=new ArrayList<Map>();
		int addrCount=0;
		for (Token tnok : ml) {
//		   Object[] strs=map.keySet().toArray();
//		   String key=(String)strs[0];
			String strs=tnok.name;
			String val=tnok.code;
			boolean rep=false;
			int amg=114514;
			for(Token tmpTkn:easy) {
				if (tmpTkn.name.equals(strs)) {
					rep=true;
					amg=tmpTkn.addr;
					tnok.setAddr(amg);
					break;
				}
			}
			if (rep) {
				continue;}
			else {;}
			if (val.equals("18")||val.equals("19")||val.equals("20")){
				   Map<String, String> tmp=new HashMap<String, String>();
				   tmp.put(strs, val);
				   easy.add(new Token(strs, val,addrCount));
				   tnok.setAddr(addrCount);
				   addrCount++;
				   result.add(tmp);
			}

		}
		return result;
	}
	public List<Map> analyse(char[] target,List<Token> tks) {
		List<Map> mList=new ArrayList<Map>();
		StringBuffer tmpsb=new StringBuffer();
		for (int i=0;i<target.length;i++) {
			Map<String, String> mp=new HashMap<String, String>();
			tmpsb.setLength(0);
			char tmpch=target[i];
			if (tmpch=='\t'||tmpch==' '||tmpch=='\n'||tmpch=='\r') {
				continue;
			}
			else if (isLetter(tmpch)) {
//				tmpsb.append(tmpch);
			    boolean dividerMark;
				while (isDigit(tmpch)||isLetter(tmpch)) {
				    if (';'==tmpch) {
				        dividerMark=true;
				    }
				    else {dividerMark=false;}
					tmpsb.append(tmpch);
					tmpch=target[++i];
				}
				i--;//因为在最后一个字符处，
				if (!isKwd(tmpsb.toString())) {
					mp.put(tmpsb.toString(), "18");
				}
				else {
					switch (tmpsb.toString()) {
					case "and":mp.put("and", "1"); break;
					case "begin":mp.put("begin", "2"); break;
					case "bool":mp.put("bool", "3"); break;
					case "do":mp.put("do", "4"); break;
					case "else":mp.put("else", "5"); break;
					case "end":mp.put("end", "6"); break;
					case "false":mp.put("false", "7"); break;
					case "if":mp.put("if", "8"); break;
					case "integer":mp.put("integer", "9"); break;
					case "not":mp.put("not", "10"); break;
					case "or":mp.put("or", "11"); break;
					case "program":mp.put("program", "12"); break;
					case "real":mp.put("real", "13"); break;
					case "then":mp.put("then", "14"); break;
					case "true":mp.put("true", "15"); break;
					case "var":mp.put("var", "16"); break;
					case "while":mp.put("while", "17"); break;
					default:
						break;
					}
				}
			}//标识符或关键字
			else if (isDigit(tmpch)||tmpch=='.') {
				while(isDigit(tmpch)||(tmpch=='.'&&isDigit(target[i+1]))){
					tmpsb.append(tmpch);
					tmpch=target[++i];
					if (isLetter(tmpch)) {
						System.out.println("Letter in num,location"+i);
						break;
					}
				}
				int real=0;
				for(char ist:tmpsb.toString().toCharArray()) {
					if (ist=='.') {
						real=1;
					}
				}
				int psk=(real==0?19:20);
				mp.put(tmpsb.toString(), psk+"");
//				mp.put(tmpsb.toString(), );
			}//整数和实数
			else switch (tmpch) {
			case '(':mp.put("(", "21");break;
			case '#':mp.put("#", "0");break;
			case ')':mp.put(")", "22");break;
			case ';':mp.put(";", "30");break;
			case '.':mp.put(".", "27");break;
			case ',':mp.put(",", "28");break;
			case '+':mp.put("+", "23");break;
			case '-':mp.put("-", "24");break;
			case '*':mp.put("*", "25");break;
			case '/':mp.put("/", "26");break;
			case ':':{
				tmpch=target[++i];
				if (tmpch=='=') {
					mp.put(":=","31");
				}
				else {
					mp.put(":", "29");
					i--;
				}
			}break;
			case '=':{
				tmpch=target[++i];
				if (tmpch=='=') {
					mp.put("==","38" );
				}
				else {
					mp.put("=", "32");
					i--;
				}
			}break;
			case '<':{
				tmpch=target[++i];
				if (tmpch=='=') {
					mp.put("<=","33" );
				}
				else if (tmpch=='>') {
					mp.put("<>","35");
				}
				else {
					mp.put("<", "34");
					i--;
				}
			}break;
			case '>':{
				tmpch=target[++i];
				if (tmpch=='=') {
					mp.put(">=","37" );
				}
				else {
					mp.put(">", "36");
					i--;
				}
			}break;
			default:
				mp.put("EXCEPTION", "114");
				System.out.println("位置"+i+"检出错误,字符"+tmpch);
				break;
			}//其他字符
			mList.add(mp);
			Object[] strs=mp.keySet().toArray();
			String key=(String)strs[0];
			String val=(String) mp.get(key);
//			tks.add(new Token(key, val));
			if (!(val.equals("18")||val.equals("19")||val.equals("20"))) {
				tks.add(new Token(key, val,-1));
			}
			else if (val.equals("114")) {
                continue;
            }
			else {
				tks.add(new Token(key, val,114514));
			}
		}//遍历字符表
		
		int a=0;
		return mList;
	}//分析器方法
}//外层类

public class ScannerTkn {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File origin=new File("D:\\data.txt");
		FileReader fr=new FileReader(origin);
		int len=(int) origin.length();
		char[] reads=new char[len+1];
		fr.read(reads);
		fr.close();
		List finToken=new ArrayList<Token>();
		List finSymbol=new ArrayList<Token>();
		List tokens=new analysizer().analyse(reads,finToken);
		List symbols=new analysizer().generateSymbol(finToken,finSymbol);
		int a=0;
		
	}
	public List<Token> listTkn() throws IOException {
		File origin=new File("D:\\data.txt");
		FileReader fr=new FileReader(origin);
		int len=(int) origin.length();
		char[] reads=new char[len+1];
		fr.read(reads);
		fr.close();
		List<Token> finToken=new ArrayList<Token>();
		List finSymbol=new ArrayList<Token>();
		List<Map> tokens=new analysizer().analyse(reads,finToken);
		List symbols=new analysizer().generateSymbol(finToken,finSymbol);
		int a=0;
		return finToken ;
	}
	public List<Token> listSym() throws IOException {
		File origin=new File("D:\\data.txt");
		FileReader fr=new FileReader(origin);
		int len=(int) origin.length();
		char[] reads=new char[len+1];
		fr.read(reads);
		fr.close();
		ArrayList<Token> finToken=new ArrayList<Token>();
		ArrayList<Token> finSymbol=new ArrayList<Token>();
		List tokens=new analysizer().analyse(reads,finToken);
		List symbols=new analysizer().generateSymbol(finToken,finSymbol);
		int a=0;
		return finSymbol ;
	}

}



//and	1
//begin	2
//bool	3
//do	4
//else	5
//end	6
//FALSE	7
//if	8
//integer	9
//not	10
//or	11
//program	12
//real	13
//then	14
//TRUE	15
//var	16
//while	17
//标识符	18
//整数	19
//实数	20
//（	21
//）	22
//+	23
//-	24
//*	25
///	26
//.	27
//，	28
//:	29
//；	30
//:=	31
//=	32
//<=	33
//< 	34
//<> 	35
//> 	36
//>=	37
//==    38
//错误：114



