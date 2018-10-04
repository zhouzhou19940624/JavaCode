package com.easywork.javatest;

public class Ttt {
	public static void main(String[] args) {
		String str = "zhou.text";
		int lastIndexOf = str.lastIndexOf(".");
		String substring = str.substring(0, lastIndexOf);
		System.out.println(substring);
	}
}
