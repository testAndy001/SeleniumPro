package cn.gloryroad.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {
	Properties properties;

	public ObjectMap(String ProFile) {
		properties = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(ProFile);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			
			System.out.println("读取文件对象出错");
			e.printStackTrace();
		}

	}
	/*
	 * 变量ElementNameInputFile属于键，变量locator属于键值，
	 * 通过键值找到objectMap.properties文件中的元素的定位类型和定位表达式
	 */
	public By getLocator(String ElementNameInputFile) throws Exception {
		String locator = properties.getProperty(ElementNameInputFile);
		// 将配置对象中的定位类型存储到locatorType变量中，将定位表达式的值存储到locatorValue中
		// 获取的键值字符串通过>分割
		String locatorType = locator.split(">")[0];//定位类型
		String locatorValue = locator.split(">")[1];//定位表达式
		locatorValue = new String(locatorValue.getBytes("ISO-8859-1"), "UTF-8");
		// 输出locatorType和locatorValue变量值，验证赋值是否正确
		System.out.println("获取的定位类型:" + locatorType + "\t获取的定位表达式:" + locatorValue);
		// 根据locatorType的变量值内容判断返回何种定位方式的by对象
		if (locatorType.toLowerCase().equals("id")) {
			return By.id(locatorValue);
		} else if (locatorType.toLowerCase().equals("name")) {
			return By.name(locatorValue);

		} else if (locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class")) {
			return By.className(locatorValue);

		} else if (locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag")) {
			return By.tagName(locatorValue);

		} else if (locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link")) {
			return By.linkText(locatorValue);

		} else if (locatorType.toLowerCase().equals("partiallinktext")) {
			return By.partialLinkText(locatorValue);

		} else if (locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css")) {
			return By.cssSelector(locatorValue);

		} else if (locatorType.toLowerCase().equals("xpath")) {
			return By.xpath(locatorValue);

		} else
			throw new Exception("输入的locatorType未在程序中定义:" + locatorType);

	}
}