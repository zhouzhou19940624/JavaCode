package com.easywork.mystery.controller;

import com.easycore.utils.HdfsUtils;

public class Test {
public static void main(String[] args) {
	try {
		/*HdfsUtils.deletePath("/zhouzhou19940624");*/
		HdfsUtils.listHdfsPath("/zhouzhou19940624");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
