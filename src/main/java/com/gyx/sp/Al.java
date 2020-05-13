package com.gyx.sp;

import java.util.*;

public class Al {
	public static void main(String[]args){
		Scanner s = new Scanner(System.in);
		while(true) {
			System.out.print("输入你的姓名：");
			String name = s.nextLine().toLowerCase();
			int sum = 0;
			for(int j = 0; j< name.length();j++) {
				sum+=index(name.charAt(j));
			}
			float i = (float)sum/name.length();
			System.out.println(String.valueOf(sum)+ " " +  String.valueOf(i));
		}
    }

	private static int index(char a) {
		int result = 0;
		if(a == 'a') {
			result = 1;
		}else if (a == 'b') {
			result = 2;
		}else if (a == 'c') {
			result = 3;
		}else if (a == 'd') {
			result = 4;
		}else if (a == 'e') {
			result = 5;
		}else if (a == 'f') {
			result = 6;
		}else if (a == 'g') {
			result = 7;
		}else if (a == 'h') {
			result = 8;
		}else if (a == 'i') {
			result = 9;
		}else if (a == 'j') {
			result = 10;
		}else if (a == 'k') {
			result = 11;
		}else if (a == 'l') {
			result = 12;
		}else if (a == 'm') {
			result = 13;
		}else if (a == 'n') {
			result = 14;
		}else if (a == 'o') {
			result = 15;
		}else if (a == 'p') {
			result = 16;
		}else if (a == 'q') {
			result = 17;
		}else if (a == 'r') {
			result = 18;
		}else if (a == 's') {
			result = 19;
		}else if (a == 't') {
			result = 20;
		}else if (a == 'u') {
			result = 21;
		}else if (a == 'v') {
			result = 22;
		}else if (a == 'w') {
			result = 23;
		}else if (a == 'x') {
			result = 24;
		}else if (a == 'y') {
			result = 25;
		}else if (a == 'z') {
			result = 26;
		}
		
		return result;
	}  
}
