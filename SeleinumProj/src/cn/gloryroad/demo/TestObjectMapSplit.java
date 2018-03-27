package cn.gloryroad.demo;

import org.openqa.selenium.By;

import cn.gloryroad.util.ObjectMap;

public class TestObjectMapSplit {

	public static void main(String[] args) throws Exception {
		/*
		 * 测试运行ObjectMap类中方法是否可用
		 * 获取的定位类型:id	 获取的定位表达式:idInput By.id: idInput
		 */
		ObjectMap objectMap=new ObjectMap("F:\\workspace\\SeleinumProj\\objectMap.properties");
		By b1=objectMap.getLocator("login.username");
		By b2=objectMap.getLocator("login.password");
		System.out.print(b1);
		System.out.print(b2);
	}

}
