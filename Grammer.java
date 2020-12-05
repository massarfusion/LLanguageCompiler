package grammar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import scanning.*;
public class Grammer {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ScannerTkn scn=new ScannerTkn();
		List<Token> tokens=scn.listTkn();
		List<Token> symbols=scn.listSym();
		Stack<String> processStack=new Stack<String>();
		Stack<String> readStack=new Stack<String>();
		readStack.push("#");
		processStack.push("#");
		processStack.push("L");
		for (int i=tokens.size()-1;i>=0;i--) {
		    if ("114".equals(tokens.get(i).getCode())) {
		        continue;
		    }
		    else {
		        readStack.push(tokens.get(i).getCode());                
            }
		}
		int a=0;
		boolean success=decrease(processStack,readStack);
	}
	
	public static boolean decrease(Stack<String>processStack,Stack<String>readStack) {
	    StringBuffer dec=new StringBuffer();
        while (!  ( "#".equals(processStack.peek())&&"#".equals(readStack.peek()) ) ) {
            if (!processStack.peek().equals("B")) {
                String left=processStack.peek();//产生式左部
                String[] tmpSq=getSeq(processStack.peek(), readStack.peek()); //产生式右部
                if (tmpSq[0].equals("ERROR")) {
                    System.out.println("Fatal error,Exiting...");
                    return false;
                }
                else {
                    ;
                }
                dec.append(left);dec.append("->");
                for ( String s:tmpSq) {
                    dec.append(s);dec.append(" ");
                }//显示打印用
                processStack.pop();
                for (int i=0;i<tmpSq.length;i++) {
                    String tmp=tmpSq[tmpSq.length-i-1];
                    if("".equals(tmp)) {continue;}
                    else {
                        ;
                    }
                    processStack.push(tmp);
                }//压栈
                if ("#".equals(readStack.peek())&&readStack.size()==1&&
                        "#".equals(processStack.peek())&&processStack.size()==1)
                {
                    System.out.println(processStack.toString()+"-----"+"上一步是"+dec+"-----"+readStack.toString());
                    System.out.println("Analyzing completed,SUCCESS");
                    return true;
                }
                else {
                    ;
                }//终止的基线条件
                while (ifEqual(processStack.peek(), readStack.peek())) {
                    System.out.println("左右相同，删去"+readStack.pop()); 
                    processStack.pop();
                }
            }            
            else {
                dec.append("in bool judgment");
                System.out.println("IN BOOL");
                boolean boolJudge= priAna(processStack, readStack);
                if (!boolJudge) {
                    System.out.println("Fatal error at "+processStack.size());
                    return false;
                }
                else {
                    System.out.println("Bool Judgment Succeeded");
                    ;
                }
                //记得加纠错机构！
                while (ifEqual(processStack.peek(), readStack.peek())) {
                    System.out.println("左右相同，删去"+readStack.pop());
                    processStack.pop();
                }
                //此处记得加入消除左右一致则消去！
            }
            String a= processStack.toString()+"-----"+"上一步是"+dec+"-----"+readStack.toString();
//            if ("[#, Z]-----H-> -----[#]".equals(a)) {
//                int yjsp=114514;
//            }
//            else {
                System.out.println(a);
//            }
            //[#, Z]-----H-> -----[#]
            dec.setLength(0);
        }//major while loop
        
	    
	    return true;
    }//递归下降
	public static boolean ifEqual(String a,String b) {
        if (a.equals(b)) {
            return true;
        }
        else if ( ("19".equals(a)||"20".equals(a))&&( "19".equals(b)||"20".equals(b) ) )
        {
            return true;
        }
        else {
            return false;
        }
    }
	public static boolean priAna(Stack<String>processStack,Stack<String>readStack) {
	    processStack.pop();
	    Stack<String> right=new Stack<String>();
	    Stack<String> left=new Stack<String>();
	    Stack<String> rightTmp=new Stack<String>();
	    right.push("#");left.push("#");
	    while (!("14".equals(readStack.peek())||"4".equals(readStack.peek()))  ) {
            rightTmp.push(readStack.pop());
        }
	    while (!rightTmp.empty()) {
            right.push(rightTmp.pop());
        }
	    String[] major=new String[50];//contains only 514 or less  strs
	    int k=1;int j=114514;
	    major[k]="#";
	    boolean recovera=false;
	    String outa="810";
	    while (true) {
	        String a;
            if (recovera) {
               a=outa;
               recovera=!recovera;
            }
            else {
                a=right.pop();
            }
            if (isTermi(major[k])) {
                j=k;
            }else {
                j=k-1;
            }
            if (priority(major[j], a)==3) {
                while (true) {
                    String Q=major[j];
                    if (isTermi(major[j-1])) {
                        j=j-1;
                    }
                    else {
                        j=j-2;
                    }
                    if (priority(major[j], Q)==1) {//modified17:56
                        break;
                    }
                    else {
                        continue;
                    }
                }//while_middle
                StringBuffer sbtmp=new StringBuffer();
                for (int i = j+1; i <=k; i++) {
                    sbtmp.append(major[i]);
                }
                String N=declude(sbtmp.toString());
                if(N.equals(sbtmp.toString())) {
                    recovera=true;
                    outa=a;
                    continue;                    
                }
                else {
                    k=j+1;
                    major[k]=N;
                    recovera=true;
                    outa=a;
                    continue;
                }
            }//主干直线if
            else {
                if (priority(major[j],a)==1) {
                    k++;
                    major[k]=a;
                    recovera=false;
                    continue;
                }
                else {
                    if (priority(major[j], a)!=2) {
                        System.out.println("Fatal error,no matching priority");
                        return false;
//                        break;
                    }
                    else {
                        if (priority(major[j], "#")==2) {
                            //有待审查
                            if ("#".equals(a)&&("#".equals(major[j]))&&(!isTermi(major[k]))) {
                                break;
                            }
                            //注意，这里是关键！
                            else {
                                System.out.println("Fatal error,unexpected error");
                                return false;
//                                break;
                            }
                        }
                        else {
                            k++;
                            major[k]=a;
                            recovera=false;
                            continue;
                        }
                    }
                }
            }//第一个分歧点else
        }
        return true;
    }//算符优先分析法
	//1< 2= 3>
	public static String[] getSeq(String waiting,String coming) {
		//L
		if ("L".equals(waiting)&&"18".equals(coming)){return new String[]{"S","Z"};}
		else if ("L".equals(waiting)&&"8".equals(coming)){return new String[]{"S","Z"};}
		else if ("L".equals(waiting)&&"17".equals(coming)){return new String[]{"S","Z"};}
		else if ("L".equals(waiting)&&"2".equals(coming)){return new String[]{"S","Z"};}
		else if ("L".equals(waiting)&&"16".equals(coming)){return new String[]{"S","Z"};}
		else if ("L".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		else if ("L".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		//Z
		else if ("Z".equals(waiting)&&"30".equals(coming)){return new String[]{"30","L"};}
		else if ("Z".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		else if ("Z".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		//S
		else if ("S".equals(waiting)&&"30".equals(coming)){return new String[]{""};}
		else if ("S".equals(waiting)&&"18".equals(coming)){return new String[]{"18","31","E"};}
		else if ("S".equals(waiting)&&"8".equals(coming)){return new String[]{"8","B","14","S","Q"};}
		else if ("S".equals(waiting)&&"5".equals(coming)){return new String[]{""};}
		else if ("S".equals(waiting)&&"17".equals(coming)){return new String[]{"17","B","4","S"};}
		else if ("S".equals(waiting)&&"2".equals(coming)){return new String[]{"2","L","6"};}
		else if ("S".equals(waiting)&&"16".equals(coming)){return new String[]{"16","D"};}
		else if ("S".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		//E
		else if ("E".equals(waiting)&&"18".equals(coming)){return new String[]{"T","H"};}
		else if ("E".equals(waiting)&&"23".equals(coming)){return new String[]{"23","T","H"};}
		else if ("E".equals(waiting)&&"24".equals(coming)){return new String[]{"24","T","H"};}
		else if ("E".equals(waiting)&&"19".equals(coming)||"E".equals(waiting)&&"20".equals(coming))
		{return new String[]{"T","H"};}
		else if ("E".equals(waiting)&&"21".equals(coming)){return new String[]{"T","H"};}
		//H
		else if ("H".equals(waiting)&&"30".equals(coming)){return new String[]{""};}
		else if ("H".equals(waiting)&&"23".equals(coming)){return new String[]{"M","H"};}
		else if ("H".equals(waiting)&&"24".equals(coming)){return new String[]{"M","H"};}
		else if ("H".equals(waiting)&&"22".equals(coming)){return new String[]{""};}
		else if ("H".equals(waiting)&&"5".equals(coming)){return new String[]{""};}
		else if ("H".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		else if ("H".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		//M
		else if ("M".equals(waiting)&&"23".equals(coming)){return new String[]{"23","T"};}
		else if ("M".equals(waiting)&&"24".equals(coming)){return new String[]{"24","T"};}
		//T
		else if ("T".equals(waiting)&&"18".equals(coming)){return new String[]{"F","W"};}
		else if ("T".equals(waiting)&&"19".equals(coming)||"T".equals(waiting)&&"20".equals(coming))
		{return new String[]{"F","W"};}
		else if ("T".equals(waiting)&&"21".equals(coming)){return new String[]{"F","W"};}
		//W
		else if ("W".equals(waiting)&&"30".equals(coming)){return new String[]{""};}
		else if ("W".equals(waiting)&&"23".equals(coming))
		{return new String[]{""};}
		else if ("W".equals(waiting)&&"24".equals(coming)){return new String[]{""};}
		else if ("W".equals(waiting)&&"25".equals(coming)){return new String[]{"N","W"};}
		else if ("W".equals(waiting)&&"26".equals(coming)){return new String[]{"N","W"};}
		else if ("W".equals(waiting)&&"22".equals(coming)){return new String[]{""};}
		else if ("W".equals(waiting)&&"5".equals(coming)){return new String[]{""};}
		else if ("W".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		else if ("W".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		//N
		else if ("N".equals(waiting)&&"25".equals(coming)){return new String[]{"25","F"};}
		else if ("N".equals(waiting)&&"26".equals(coming)){return new String[]{"26","F"};}
		//F
		else if ("F".equals(waiting)&&"18".equals(coming)){return new String[]{"18"};}
		else if ("F".equals(waiting)&&"19".equals(coming)||"F".equals(waiting)&&"20".equals(coming))
		{return new String[]{"19"};}
		else if ("F".equals(waiting)&&"21".equals(coming)){return new String[]{"21","E","22"};}
		//Q
		else if ("Q".equals(waiting)&&"30".equals(coming)){return new String[]{""};}
		else if ("Q".equals(waiting)&&"5".equals(coming)){return new String[]{"5","S"};}
		else if ("Q".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		else if ("Q".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		//D
		else if ("D".equals(waiting)&&"18".equals(coming)){return new String[]{"A","29","K","30","R"};}
		//R
		else if ("R".equals(waiting)&&"30".equals(coming)){return new String[]{""};}
		else if ("R".equals(waiting)&&"18".equals(coming)){return new String[]{"D"};}
		else if ("R".equals(waiting)&&"5".equals(coming)){return new String[]{""};}
		else if ("R".equals(waiting)&&"6".equals(coming)){return new String[]{""};}
		else if ("R".equals(waiting)&&"#".equals(coming)){return new String[]{""};}
		
		//A
		else if ("A".equals(waiting)&&"18".equals(coming)){return new String[]{"18","X"};}
		//X
		else if ("X".equals(waiting)&&"29".equals(coming)){return new String[]{""};}
		else if ("X".equals(waiting)&&"28".equals(coming)){return new String[]{"28","A"};}
		//K
		else if ("K".equals(waiting)&&"9".equals(coming)){return new String[]{"9"};}
		else if ("K".equals(waiting)&&"3".equals(coming)){return new String[]{"3"};}
		else if ("K".equals(waiting)&&"13".equals(coming)){return new String[]{"13"};}
		//ELSE
		else {System.out.println("Error");
				return new String[] {"ERROR"};
		}
		}//METHOD
	public static int priority(String a,String b) {
	    if("21".equals(a)&&( "11".equals(b)||"1".equals(b)||"10".equals(b)||
	            "15".equals(b)||"7".equals(b)||"21".equals(b)||
	            "33".equals(b)||"37".equals(b)||"34".equals(b)||
	            "36".equals(b)||"38".equals(b)||"35".equals(b)||"23".equals(b)||
	            "24".equals(b)||"25".equals(b)||"26".equals(b)||
	            "18".equals(b)||"19".equals(b)||"20".equals(b)) ) 
	    {return 1;}
	    else if ( ("22".equals(b)||"11".equals(b)) 
	            && ( "11".equals(a)||"1".equals(a)||"10".equals(a)
	                    ||"15".equals(a)||"7".equals(a)||
	                    "22".equals(a)||"23".equals(a)||"24".equals(a)||
	                    "25".equals(a)||"26".equals(a)||"34".equals(a)||
	                    "36".equals(a)||"33".equals(a)||"37".equals(a)||
	                    "38".equals(a)||"35".equals(a)||"19".equals(a)||"18".equals(a)||
	                    "20".equals(a)) ) 
	    {return 3;}
	    else if (  "11".equals(a)&&( "1".equals(b)||"10".equals(b)||"18".equals(b)||
                "15".equals(b)||"7".equals(b)||"21".equals(b)||
                "34".equals(b)||"36".equals(b)||"33".equals(b)||
                "37".equals(b)||"38".equals(b)||"35".equals(b)||"23".equals(b)||
                "24".equals(b)||"25".equals(b)||"26".equals(b)||
                "19".equals(b)||"20".equals(b))  ) 
	    {return 1;}
	    else if ( ("1".equals(b)) 
                && ( "1".equals(a)||"10".equals(a)||
                        "18".equals(a)||"15".equals(a)||"7".equals(a)||
                        "22".equals(a)||"23".equals(a)||"24".equals(a)||
                        "25".equals(a)||"26".equals(a)||"34".equals(a)||
                        "36".equals(a)||"33".equals(a)||"37".equals(a)||
                        "38".equals(a)||"35".equals(a)||"19".equals(a)||
                        "20".equals(a)) ) 
        {return 3;}
	    else if (("1".equals(a)||"10".equals(a))&&
	            ("10".equals(b)||"18".equals(b)||
                "15".equals(b)||"7".equals(b)||"21".equals(b)||
                "34".equals(b)||"36".equals(b)||"33".equals(b)||
                "37".equals(b)||"38".equals(b)||"35".equals(b)||"23".equals(b)||
                "24".equals(b)||"25".equals(b)||"26".equals(b)||
                "19".equals(b)||"20".equals(b) ||"21".equals(b)) ) 
        {return 1;}
	    else if ( ("22".equals(a)||"23".equals(a)||"25".equals(a)
	            ||"26".equals(a)||"18".equals(a)||"19".equals(a) 
	            ||"20".equals(a)||"24".equals(a))
                && ( "34".equals(b)||"33".equals(b)||"36".equals(b)||
                        "37".equals(b)||"38".equals(b)||"35".equals(b)
                        ||"23".equals(b)||"24".equals(b)
                         ) )
        {return 3;}
	    else if ( ("34".equals(a)||"36".equals(a)||"33".equals(a)
                ||"37".equals(a)||"38".equals(a)||"35".equals(a)) 
                && ( "23".equals(b)||"24".equals(b)||"25".equals(b)||
                        "26".equals(b)||"18".equals(b)||"19".equals(b)
                        ||"20".equals(b)||"21".equals(b)
                         ) )
        {return 1;}
	    else if ( ("23".equals(a)||"24".equals(a)) 
                && ( 
                        "25".equals(b)||"26".equals(b)||
                        "20".equals(b)||"18".equals(b)||"19".equals(b)
                        ||"21".equals(b)
                   ) 
                )
        {return 1;}
	    else if ( ("25".equals(b)||"26".equals(b)) 
                && ( 
                        "18".equals(b)||"19".equals(b)||
                        "20".equals(b)||"21".equals(b)
                   ) 
                )
        {return 1;}
	    else if ( ("#".equals(a)) 
                && ( 
                        "33".equals(b)||"15".equals(b)||
                        "7".equals(b)||"21".equals(b)||"34".equals(b)
                        ||"35".equals(b)||"36".equals(b)||"37".equals(b)
                        ||"38".equals(b)||"18".equals(b)||"23".equals(b)
                        ||"24".equals(b)||"25".equals(b)||"26".equals(b)
                        ||"20".equals(b)||"19".equals(b)
                   ) 
                )
        {return 1;}//#1
	    else if ( 
	            ("#".equals(b)) 
                && ( "21".equals(a)||"15".equals(a)||
                        "7".equals(a)||"25".equals(a)||"26".equals(a)||
                        "18".equals(a)||"19".equals(a)||"20".equals(a)||
                        "22".equals(a)||"23".equals(a)||"24".equals(a)||
                        "34".equals(a)||"36".equals(a)||"33".equals(a)||
                        "37".equals(a)||"38".equals(a)||"35".equals(a)
                ))
        {return 3;}//#2
	    else if ( 
	                "21".equals(a)&&"22".equals(b)
                )
        {return 2;}
	    else if ( 
                "#".equals(a)&&"#".equals(b)
            )
    {return 2;}
	    else if (
	         (   "25".equals(a)||"26".equals(a)||"22".equals(a)||
	            "18".equals(a)||"19".equals(a)||"20".equals(a)  )
	         &&
	         (  "25".equals(b)||"26".equals(b)   )
            )
	    {return 3;}
	    else {
            return 114514;
        }
	  //1< 2= 3>
    }//priority
	public static boolean isTermi(String tst) {
        if ("0".equals(tst)||"1".equals(tst)||"2".equals(tst)||"3".equals(tst)||"4".equals(tst)||
"5".equals(tst)||"6".equals(tst)||"7".equals(tst)||"8".equals(tst)||"9".equals(tst)||
"10".equals(tst)||"11".equals(tst)||"12".equals(tst)||"13".equals(tst)||"14".equals(tst)||
"15".equals(tst)||"16".equals(tst)||"17".equals(tst)||"18".equals(tst)||"19".equals(tst)||
"20".equals(tst)||"21".equals(tst)||"22".equals(tst)||"23".equals(tst)||"24".equals(tst)||
"25".equals(tst)||"26".equals(tst)||"27".equals(tst)||"28".equals(tst)||"29".equals(tst)||
"30".equals(tst)||"31".equals(tst)||"32".equals(tst)||"33".equals(tst)||"34".equals(tst)||
"35".equals(tst)||"36".equals(tst)||"37".equals(tst)||"38".equals(tst)||"#".equals(tst)) {
            return true;
        }
        else {
            return false;
        }
    }
	public static String declude(String input) {
	    return "X";
//        if ("#B#".equals(input)) {
//            return "X";
//        }
//        else if ("15".equals(input)||"7".equals(input)||"21C22".equals(input)||
//                "G".equals(input)) {
//            return "B";
//        }
//        else if ("15".equals(input)||"7".equals(input)) {
//            return "C";
//        }
//        else if ("H1J".equals(input)||"J".equals(input)) {
//            return "H";
//        }
//        else if ("10J".equals(input)||"B".equals(input)) {
//            return "J";
//        }
//        else if ("E33E".equals(input)||"E34E".equals(input)||"E35E".equals(input)||
//                "E35E".equals(input)||"E36E".equals(input)||"E37E".equals(input)||"E".equals(input)) {
//            return "G";
//        }
//        else if ("24T".equals(input)||"23T".equals(input)||"T".equals(input)||
//                "E23T".equals(input)||"E24T".equals(input)) {
//            return "E";
//        }
//        else if ("F".equals(input)||"T25F".equals(input)||"T26F".equals(input)) {
//            return "T";
//        }
//        else if ("19".equals(input)||"18".equals(input)||"21E22".equals(input)||"20".equals(input)) {
//            return "F";
//        }
//        
//        else {
//            System.out.println("Fatal error:cannot reverse to left!");
//            return input;
//        }
    }//规约
	}//CLASS
