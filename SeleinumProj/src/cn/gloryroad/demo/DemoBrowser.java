package cn.gloryroad.demo;

import cn.gloryroad.configuration.KeyWordsAction;
import cn.gloryroad.util.WaitUtil;

public class DemoBrowser {

	public static void main(String[] args) {
		// 验证各个浏览器是否可用
		/*
		 * System.setProperty("webdriver.ie.driver","C:\\IEDriverServer.exe");
		 * WebDriver driver = new InternetExplorerDriver();
		 */
		/*
		 * System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
		 * WebDriver driver = new FirefoxDriver();
		 */
		KeyWordsAction.open_browser("IE浏览器","ie");
		WaitUtil.sleep(3000);
		KeyWordsAction.navigate("http://mail.163.com","");
		WaitUtil.sleep(3000);
	}

}
