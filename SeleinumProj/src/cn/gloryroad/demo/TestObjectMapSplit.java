package cn.gloryroad.demo;

import org.openqa.selenium.By;

import cn.gloryroad.util.ObjectMap;

public class TestObjectMapSplit {

	public static void main(String[] args) throws Exception {
		/*
		 * ��������ObjectMap���з����Ƿ����
		 * ��ȡ�Ķ�λ����:id	 ��ȡ�Ķ�λ���ʽ:idInput By.id: idInput
		 */
		ObjectMap objectMap=new ObjectMap("F:\\workspace\\SeleinumProj\\objectMap.properties");
		By b1=objectMap.getLocator("login.username");
		By b2=objectMap.getLocator("login.password");
		System.out.print(b1);
		System.out.print(b2);
	}

}
