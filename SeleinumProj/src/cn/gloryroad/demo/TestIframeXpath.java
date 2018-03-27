package cn.gloryroad.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import cn.gloryroad.configuration.KeyWordsAction;
import cn.gloryroad.util.WaitUtil;

public class TestIframeXpath {

	public static void main(String[] args) throws Exception {
		// 打开网易126邮箱
		KeyWordsAction.open_browser("IE浏览器","ie");
		WaitUtil.sleep(3000);
		KeyWordsAction.navigate("打开浏览器","http://mail.126.com");
		WaitUtil.sleep(3000);
		//切换到框架中，操作框架中元素
		WebElement iframe=KeyWordsAction.driver.findElement(By.tagName("iframe"));
		KeyWordsAction.driver.switchTo().frame(iframe);
		WebElement username=KeyWordsAction.driver.findElement(By.name("email"));
		WebElement userpwd=KeyWordsAction.driver.findElement(By.name("password"));
		WebElement userbtn=KeyWordsAction.driver.findElement(By.id("dologin"));
		
		username.clear();
		username.sendKeys("testAndy001");
		userpwd.sendKeys("123qwe!");
		userbtn.click();
		WaitUtil.sleep(3000);
		//关闭对话框
		KeyWordsAction.handleconfirm("","");
		WaitUtil.sleep(3000);
		//返回主界面
		KeyWordsAction.driver.switchTo().defaultContent();
		
	}
}
