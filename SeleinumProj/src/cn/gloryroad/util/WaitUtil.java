package cn.gloryroad.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	//用于测试执行过程中暂停程序执行的休眠方法
	public static void sleep(long millisecond){
		try {
			//线程休眠millisecond定义的毫秒数
			Thread.sleep(millisecond);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	//显示等待页面元素出现的封装方法，参数为页面元素的Xpath定位字符串
	public static void waitWebElement(WebDriver driver,String xpathExpesston){
		WebDriverWait wait=new WebDriverWait(driver,10);
		//调用ExpectedConditions的presenceofElementLocated方法判断页面是否出现
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpesston)));
		
	}
	//显示等待页面元素出现的封装方法，，参数为页面元素的By对象，此函数可以支持更多的定位表达式
	public static void waitWebElement(WebDriver driver,By by){
		WebDriverWait wait=new WebDriverWait(driver,10);
		//调用ExpectedConditions的presenceOfElementLocated方法判断页面元素是否出现
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
}
