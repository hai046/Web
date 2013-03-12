//package com;
/**
 * @author lurma
 * Copyright ~
 * All right reserved
 * 
 */

import java.util.ArrayList;
import java.util.Stack;
public class PolynomialCalculation{
	//�����ַ����͵Ķ�ջ o_stack �������洢�����
	private static Stack<String> o_stack;
    //�����ַ����͵Ķ�ջ d_stack �������洢����
	private static Stack<String> d_stack;
	//������������֣�#����Ϊ����ʽ�Ľ����־
	private static String [] Operator = {"#","(",")","+","-","*","/"};
	//�����������飬�����洢���������ȼ�
	private static int [] Priority = {0,1,1,2,2,3,3};
	private static int error = 0;
	//���췽��
	public PolynomialCalculation(){
		
	}
	//��ʼ��������ջ
	private static void init(){
    	o_stack = new Stack<String>();
		d_stack = new Stack<String>();
    }
	//�ж��Ƿ���������
	private static boolean isDigit(String op){
		//����Ĳ�������Ǹ������� -5.8 �������Ҫ�ж��������Ƿ��и��ź͵�ŵ����
		if(op.equals("-")) {
			//˵���������һ������
			return false;
		}
		//���� op ������з����֡��ǵ�źͷǸ��ŵ��ַ���˵��������ַ���������
		for(int i = 0; i < op.length(); i++){
			if((int)op.charAt(i) < (int)'0' || (int)op.charAt(i) > (int)'9'){
				if(op.charAt(i) != '.' && op.charAt(i) != '-'){
					return false;
				}
			}
		}
		return true;
	}
	//�жϴ�����ַ��Ƿ��������
	private static boolean isOperator(String op){
    	for(int i  = 0; i < Operator.length; i++)
    		if(op.equals(Operator[i]))
    			return true;
    	return false;
    } 
	//���ش�������������ȼ�
	private static int getPriority(String op){
    	for(int i  = 0; i < Operator.length; i++)
    		if(op.equals(Operator[i]))
    			return Priority[i];
		// -1 ��?��������Ϸ�
    	return -1;
    }
    //�˷���ʵ�ֶ��ַ���зָ��������������������ַ����͵�������
	private static ArrayList<String> getList(String str){
		//�����ַ����͵����� mList
    	ArrayList<String> mList = new ArrayList<String>();
    	// aList �����洢���ո����ַ�
		String aList = "";
		//��� str �еĿո�
    	for(int i = 0; i < str.length(); i++){
    		if(str.charAt(i) != ' '){
    			aList += String.valueOf(str.charAt(i));
    		}
    	}
        //��ȡ ���ո��� aList �ĳ���
    	int length = aList.length();
    	int i = 0;
    	error = 0;
    	while(i < length){
			//���ǰ�ַ�ʱ�����
    		if(isOperator(String.valueOf(aList.charAt(i)))){
				//����ǰ�ַ�ת��Ϊ�ַ����뵽������
    			mList.add(String.valueOf(aList.charAt(i)));
				//ָ���1
    			i++;
            //��ǰ�ַ�ʱ����
    		}else if((int)aList.charAt(i) >= (int)'0' && (int)aList.charAt(i) <= (int)'9'){
    			//��ȡ���ֵĿ�ʼλ��
				int start = i;
				//flag��������С���ĸ���
    			int flag = 0;
    			i++;
				//�ӳ������ֵ�λ�ÿ�ʼ���ɨ��
    			while(i < length){
					//���ǰ�ַ�������
    				if((int)aList.charAt(i) >= (int)'0' && (int)aList.charAt(i) <= (int)'9'){
    					//�������ɨ��
						i++;
					//���ǰ�ַ�ʱС���
    				}else if(aList.charAt(i) == '.'){
    					//flag��������1
						flag ++;
    					i++;
					//����������ַ������ while ѭ��
    				}else break;
    			}
				//��� flag > 1 ˵����һ�����������������Լ��������ϵ�С��㣬���Ƿ�С��
    			if(flag > 1){
    				//javax.swing.JOptionPane.showMessageDialog(null, "����ʽ���зǷ�С��!");
    				error = 1;
    			//��� flag <= 1 ˵��С������Ϸ�
				}else{
					//����ȡ�������������������
    				mList.add(aList.substring(start,i));
    			}
            //����ǿո�ָ���1 ����ǰ�淽�����Ѿ����ַ�
			//��������մ��?��˴˶δ��벻�Ǳ����
    		}else if(aList.charAt(i) == ' '){
    			i++;
    		}else{
    			//javax.swing.JOptionPane.showMessageDialog(null, "����ʽ���������ַ�");
    			error = 1;
    			i++;
    		}
    			
    	}
		//�˶�try����catch ����ʵ�ֶԸ���Ĵ���
    	try{
			//���������λ�� �� - �� ���ҵڶ�����������
			//˵������ʽ���Ը���ͷ�ģ������Ҫ��������
			//����ȡ����������´���������
	    	if(mList.get(0).equals("-") && isDigit(mList.get(1))){
	    		
	    		if(mList.get(1).indexOf(".") == -1){
	    		    mList.set(1,String.valueOf((0 - Integer.parseInt(mList.get(1)))));
	    		}else{
	    			mList.set(1,String.valueOf((0 - Float.parseFloat(mList.get(1)))));
	    		}
				//��������λ�ķ���Ƴ�
	    		mList.remove(0);
	    	}
			//����������ڴ��ڸ���������� ( -2 + 4 ) ��������
			//������ͬ��
	    	for(i = 0; i < mList.size(); i++){
	    		if(mList.get(i).equals("(") && mList.get(i + 1).equals("-") && isDigit(mList.get(i + 2))){
	    			if(mList.get(i + 1).equals("-")){
	    				if(mList.get(i + 2).indexOf(".") == -1){
	    					mList.set(i + 2,String.valueOf((0 - Integer.parseInt(mList.get(i + 2)))));
	    	    		}else{
	    	    			mList.set(i + 2,String.valueOf((0 - Float.parseFloat(mList.get(i + 2)))));
	    	    		}
	    	    		mList.remove(i + 1);
	    			}
	    		}
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		//���ش���������
    	return mList;
    }
    //����������������ʵ�ּ򵥵���������
	private static String Operate(String x,String Operator,String y){
    	//��ȡ����������
		char ch = Operator.charAt(0);
    	//��ȡ������
		float numA = Float.parseFloat(x);
        float numB = Float.parseFloat(y);
        // numC �������������
		float numC = 0;
		//switch ���ʵ������
		//������Ҫע�⣬������λ���Ƿ��ģ���Ӧ��ջ�ĳ�ջ���
    	switch(ch){
    		case '+' : numC = numB + numA;
    		           break;
    		case '-' : numC = numB - numA;
    		           break;
    		case '*' : numC = numB * numA;
    		           break;
            case '/' : numC = numB / numA;
    		           break;
    		default : break;
    	}
		//����������
    	return String.valueOf(numC);
    }
	
	//�����㷨
	//��Ҫ���ö�ջ�ĺ���ȳ���˼�룬���ȼ��ߵ������
	//��ջ�����ȼ��͵�������ջ����ջ��ͬʱ��������
	//�����ջ��������������������㣬���������ջ
	//
	/**
     * @param str - Polynomial expression
     * @return result - Operational Results
     */
	public static String getResult(String str){
		//��ջ��ʼ��
		init();
		str = str + "#";
		String result = "";
		ArrayList<String> list = getList(str);
//		for(int i = 0; i < list.size(); i++){
//			System.out.print(list.get(i) + "  ");
//		}
		int i = 0;
		try{
			while(i < list.size()){
				if(isDigit(list.get(i))){
					d_stack.push(list.get(i));
					i++;
				}else if(isOperator(list.get(i))){
					if(o_stack.isEmpty()){
						o_stack.push(list.get(i));
						i++;
					}else if(list.get(i).equals("(")){
						o_stack.push(list.get(i));
						i++;
					}else if(list.get(i).equals(")") && o_stack.peek().equals("(")){
						o_stack.pop();
		    			i++;
					}else{
					    if(getPriority(list.get(i)) > getPriority(o_stack.peek())){
							o_stack.push(list.get(i));
					    	i++;
				    	}else if(getPriority(list.get(i)) <= getPriority(o_stack.peek())){
				    		if(!o_stack.peek().equals("(") && !o_stack.peek().equals(")"))
			    			    d_stack.push(Operate(d_stack.pop(),o_stack.pop(),d_stack.pop()));
				    		else {
				    			error = 1;
				    			break;
				    		}
				    		
					    }
					}
				}
			}
			if(error == 0){
			    result = d_stack.pop();
			}
			if(!d_stack.empty())
				result = "Processing errors!";
		}catch(Exception e){
			result = "Processing errors!";
			e.printStackTrace();
		}
		return result;
	}
	public static void main (String[] args) {
		String a = "10(5";
		System.out.println(a + " = " + PolynomialCalculation.getResult(a));
    }
}
