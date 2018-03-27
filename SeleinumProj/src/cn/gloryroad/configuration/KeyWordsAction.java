package cn.gloryroad.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import cn.gloryroad.testScript.TestSuiteByExcel;
import cn.gloryroad.util.KeyBoardUtil;
import cn.gloryroad.util.Log;
import cn.gloryroad.util.ObjectMap;
import cn.gloryroad.util.WaitUtil;

public class KeyWordsAction{
	/*
	 * 声明静态WedDriver对象，用于此类相关的Driver操作
	 * 声明存储定位表达配置文件的ObjectMap对象
	 * 指定Log4j配置文件为Log4j.xml
	 */
	public static WebDriver driver;
	private static ObjectMap objectMap = new ObjectMap(Constants.Path_ConfigurationFile);
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	/*通过browserName打开浏览器*/
	@SuppressWarnings("deprecation")
	public static void open_browser(String locatorExpression,String browserName) {
		switch (browserName) {
		case "ie":
			//加载驱动器
			System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
			//设定固定的IP和监听端口
			InternetExplorerDriverService.Builder builder = new InternetExplorerDriverService.Builder();
			InternetExplorerDriverService internetExplorerService = builder.usingPort(8080).withHost("127.0.0.1").build();
			//设定IE浏览器初始化页面
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
			caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.126.com");
			driver = new InternetExplorerDriver(internetExplorerService,caps);
			Log.info("IE浏览器实例已经声明");
			break;
		case "firefox":
			//加载驱动器
			System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			driver = new FirefoxDriver();
			Log.info("火狐浏览器实例已经声明");
			break;
		case "chrome":
			//加载驱动器
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			driver = new ChromeDriver();
			Log.info("Chrome浏览器实例已经声明");
			break;
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的navigate关键字*/
	public static void navigate(String locatorExpression,String url) {	
		driver.get(url);
		Log.info("浏览器访问网址" + url);
	}
	/*获取当前页面的url地址*/
	public String CurrentPageUrl(String string1,String CurrentPageUrl){
		String GetCurrentPageUrl = null;
		try {
			GetCurrentPageUrl=driver.getCurrentUrl();
			if(CurrentPageUrl.equals(GetCurrentPageUrl)){
				Log.info("获取当前页面url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GetCurrentPageUrl;
	}
	/*此方法的名称对应Excel文件“关键字”列中的input关键字*/
	public static void input(String locatorExpression,String inputString) {
		try {
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("清除" + locatorExpression+"输入框中的所有内容");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("在" + locatorExpression+"输入框中输入"+inputString);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("在" + locatorExpression+"输入框中输入"+inputString+"出现异常" + e.getMessage());
			e.printStackTrace();
		}

	}
	/*此方法的名称对应Excel文件“关键字”列中的click关键字 */
	public static void click(String locatorExpression,String string) {
		try {
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("单击" + locatorExpression+"页面元素成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("单击" + locatorExpression+"页面元素失败"+ e.getMessage());
			e.printStackTrace();
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的press_Tab关键字，用于按Tab键的操作*/
	public static void press_Tab(String locatorExpression,String string) {
		try {
			Thread.sleep(2000);
			// 调用KeyBoardUtil类的封装方法pressTabKey
			KeyBoardUtil.pressTabKey();
			Log.info("按Tab键成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("按Tab键出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的press_enter关键字， 用于按enter键*/
	public static void press_enter(String locatorExpression,String string) {
		try {
			KeyBoardUtil.pressEnterKey();
			Log.info("按enter键成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("按enter键出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的paste_string关键字*/
	public static void paste_string(String locatorExpression,String pasteContent) {
		try {
			KeyBoardUtil.setAndCtrlVClipboardData(pasteContent);
			Log.info("成功黏贴邮件正文" + pasteContent);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("黏贴邮件正文出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的sleep关键字*/
	public static void sleep(String locatorExpression,String sleepTime) {
		try {
			WaitUtil.sleep(Integer.parseInt(sleepTime));
			Log.info("休眠" + Integer.parseInt(sleepTime) / 1000 + "秒成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("线程休眠出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*此方法的名称对应Excel文件“关键字”列中的clickButtonList关键字*/
	public static void clickButtonList(String locatorExpression,String string) {
		try {
			List<WebElement> buttons = driver.findElements(objectMap.getLocator(locatorExpression));
			buttons.get(0).click();
			Log.info("单击集合中的按钮成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("单击集合中的按钮出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}

	/*此方法的名称对应Excel文件“关键字”列中的Assert_String关键字*/
	public static void Assert_String(String locatorExpression,String assertString) {
		try {
			Assert.assertTrue(driver.getPageSource().contains(assertString));
			Log.info("成功断言关键字:" + assertString);
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult=false;
			Log.info("出现断言失败，具体异常信息" + e.getMessage());
			System.out.println("断言失败");
		}
	}

	/*此方法的名称对应Excel文件“关键字”列中的close_browser关键字 */
	public static void close_browser(String locatorExpression,String browserName) {
		try {
			System.out.println("浏览器关闭函数被执行");
			Log.info("关闭浏览器窗口");
			driver.quit();
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("关闭浏览器出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	//---------------------------------------关键字基础用法-----------------------------------------------
	/*窗口最大化*/ 
	public void BrowserWindowsMax(String string1,String string2){
		driver.manage().window().maximize();
	}
	/*声明Dimension对象,设定浏览器窗口大小*/
	public void SetBrowserWindows(String string1,int[] num){
		Dimension dimesion=new Dimension(num[0],num[1]);
		driver.manage().window().setSize(dimesion);
	}
	/*模拟浏览器刷新当前页面*/
	public void FreshCurrentPage(String string1,String string2){
		driver.navigate().refresh();
	}
	/*模拟浏览器的前进功能*/
	public void ForwardCurrentPage(String string1,String string2){
		driver.navigate().forward();
	}
	/*模拟浏览器的后退功能*/
	public void BackCurrentPage(String string1,String string2){
		driver.navigate().back();
	}
	/*识别和操作新弹出的浏览器窗口*/
	public static void OpenLinkWindow(String locatorExpression,String string){
		//将当前浏览器窗口的句柄存储到parentWindowHandle变量中
		String parentWindowHandle=driver.getWindowHandle();
		//找到页面上唯一的连接元素，存储到link变量中
		WebElement link;
		try {
			link = driver.findElement(objectMap.getLocator(locatorExpression));
			//单击链接
			link.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取当前所有打开窗口的句柄，存储到一个set容器中
		Set<String> allWindowHandles=driver.getWindowHandles();
		//存储对象不为空，遍历容器中的所有对象
		if(!allWindowHandles.isEmpty()){
			for(String allWindowHandle:allWindowHandles){
				//获取页面的标题属性，判断属性值是否string几个关键字
				String title=driver.switchTo().window(allWindowHandle).getTitle();
				if(title.equals(string)){
					Log.info("判断成立"+string);
					break;
				}else{
					Log.info("判断不成立"+string);
					break;
				}
			}
		}
		//返回最开始打开的浏览器页面
		driver.switchTo().window(parentWindowHandle);
	}
	/*将当前浏览器窗口截屏*/
	public void captureScreenInCurrentWindow(String locatorExpression,String string){
		//把当前的浏览器页面进行截图，保存到File对象中
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			//把File对象文件转换成一个png文件，保存到c盘中
			FileUtils.copyFile(srcFile,new File("c:\\目录名\\string.png"));
			Log.info("保存图片文件成功");
		} catch (IOException e) {
			Log.info("保存图片文件失败");
			e.printStackTrace();
		}
	}
	/*关闭浏览器进程*/
	public void killWindowProcess(String locatorExpression,String string){
		//关闭浏览器运行程序
		switch(string){
		case "firefox.exe":
			WindowsUtils.killByName(string);
			break;
		case "iexplore.exe":
			WindowsUtils.killByName(string);
			break;
		case "chrom.exe":
			WindowsUtils.killByName(string);
			break;
		}
	}
	//----------------------------切换到框架中，操作框架中元素------------------------------------------------
	public static void switchTo_frame(String locatorExpression,String string){
		try{
			//获取嵌入式框架对象，进入框架区域
			WebElement iframe=driver.findElement(objectMap.getLocator(locatorExpression));
			driver.switchTo().frame(iframe);
			System.out.println("在框架内");
			Log.info("进入"+locatorExpression+"框架成功");
		}catch(Exception e){
			TestSuiteByExcel.testResult=false;
			Log.info("进入"+locatorExpression+"框架失败");
			e.printStackTrace();
		}
	}
	//----------------------操作javaScript的confirm/prompt弹窗-------------------------------------------
	public static void handleconfirm(String locatorExpression,String string){
		try{
			//获取Alert对象
			Alert alert=driver.switchTo().alert();
			//单击确定按钮
			alert.accept();
			//单击取消按钮
			//alert.dismiss();
		}catch(Exception e){
			TestSuiteByExcel.testResult=false;
			Log.info("尝试操作的失败");
			e.printStackTrace();
		}
	}
	//----------------------------操作浏览器中的Cookie--------------------------------------------------
	public static void handleCookie(String locatorExpression,String string){
		Set<Cookie>cookies=driver.manage().getCookies();
		for(Cookie cookie:cookies){
			System.out.println("所在的域:"+cookie.getDomain());
			System.out.println("name:"+cookie.getName());
			System.out.println("value:"+cookie.getValue());
			System.out.println("有效日期:"+cookie.getExpiry());
			System.out.println("路径:"+cookie.getPath());
			//通过对象,删除Cookie
			Cookie newCookie=new Cookie(cookie.getName(),cookie.getValue());
			driver.manage().deleteCookie(newCookie);		
		}
		/*
		 * 删除Cookie方法1通过名字
		 * driver.manage().deleteCookieNamed("");
		 * 删除Cookie方法3删除所有
		 * driver.manage().deleteAllCookies();
		 */
		try {
			Thread.sleep(1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//----------------------------------判断页面元素是否存在---------------------------------------------
	public static boolean IfElementExist(String locatorExpression,WebElement string) {
		try {
			string=driver.findElement(objectMap.getLocator(locatorExpression));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(string!=null){
			Log.info("操作的元素找到");
			return true;
		}else{
			Log.info("操作的元素没有找到");
			return false;
		}
	}
	//---------------------------检查页面元素的文本内容是否出现------------------------------------------------
	public void IfWebElementText(String locatorExpression,String string){
		//捕获对象
		WebElement text;
		try {
			text = driver.findElement(objectMap.getLocator(locatorExpression));
			String contentText=text.getText();
			//完全匹配
			Assert.assertEquals(contentText, string);
			//文字内容是否包含String
			Assert.assertTrue(contentText.contains(string));
			//文字内容开始文字是否是String
			Assert.assertTrue(contentText.startsWith(string));
			//文字内容结束文字是否是String
			Assert.assertTrue(contentText.endsWith(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//---------------------------显示等待页面元素是否出现在页面中-------------------------------------------------------------
	/* 此方法的名称对应Excel文件“关键字”列中的WaitFor_Element关键字*/
	public static void WaitFor_Element(String locatorExpression,String string) {

		try {
			// 调用封装的waitWebElement函数显示等待页面元素是否出现
			WaitUtil.waitWebElement(driver, locatorExpression);
			Log.info("显示等待元素出现成功，元素是" +locatorExpression);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("显示等待元素出现异常，具体异常信息" + e.getMessage());
			e.printStackTrace();
		}
	}
	//---------------------------检查单选列表的选项文字是否符合期望----------------------------------------------
	public void CheckSlectText(String locatorExpression,String[] string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//设定单选列表的期望值
			List<String>expect_option=new ArrayList<String>();
			expect_option.add(string.toString());
			//获取单选列表的实际值存储到list列表中
			List<String>actual_option=new ArrayList<String>();
			for(WebElement option:dropList.getOptions()){
				actual_option.add(option.getText());
				//判断期望值是否与实际值一致
				if(expect_option.toArray().equals(actual_option.toArray())){
					Log.info("期望值与实际值一致"+expect_option.toArray());
					break;
				}else{
					Log.info("期望值与实际值不一致"+actual_option.toArray());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	//-------------------------------操作复选框--------------------------------------------------------
	/*选中复选框*/
	public void SelectCheckBox(String locatorExpression,String string){
		//获取复选框元素对象
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(locatorExpression));
			if(!checkbox.isSelected()){
				checkbox.click();
				Log.info("选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*取消复选框*/
	public void NoSelectCheckBox(String locatorExpression,String string){
		//获取复选框元素对象
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(locatorExpression));
			if(checkbox.isSelected()){
				checkbox.click();
				Log.info("取消选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*选中全部复选框*/
	public void AllSelectCheckBox(String locatorExpression,String string){
		List<WebElement> checkboxs;
		try {
			checkboxs = driver.findElements(objectMap.getLocator(locatorExpression));
			for(WebElement checkbox:checkboxs ){
				checkbox.click();
				//checkbox.click();取消全部选中的复选框
				Log.info("全部选中复选框");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//--------------------------------操作单选框-------------------------------------------------------
	/*选中单选框*/
	public void SelectRadio(String locatorExpression, String string) {
		WebElement radio;
		try {
			radio = driver.findElement(objectMap.getLocator(locatorExpression));
			if (!radio.isSelected()) {
				radio.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*取消单选框*/
	public void NoSelectRadio(String locatorExpression,String string){
		WebElement radio;
		try {
			radio = driver.findElement(objectMap.getLocator(locatorExpression));
			if (radio.isSelected()) {
				radio.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*选中全部单选框*/
	public void AllSelectRadio(String locatorExpression,String value){
		List<WebElement> radios;
		try {
			radios = driver.findElements(objectMap.getLocator(locatorExpression));
			for(WebElement radio:radios){
				if(!radio.isSelected()){
					radio.click();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//--------------------------操作多选的选择列表----------------------------------------------------------
	/*通过选项的Value属性值选中列表*/
	public void DropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//判断下拉列表是否支持多选,支持多选返回true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.selectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*通过选项的Value属性值取消选中列表*/
	public void NoDropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//判断下拉列表是否支持多选,支持多选返回true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.deselectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//---------------------------------操作单选下拉列表---------------------------------------------------
	/*通过选项列表的value值，进行选中操作*/
	public void OneDropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			boolean flag=dropList.isMultiple();
			if(flag==false){
				Log.info("进入单选下拉列表");
				dropList.selectByValue(string);
				/* 通过选项列表的下标值进行选中
				 * dropList.selectByIndex(0);
				 * 通过选项的文字进行选中
				 * dropList.selectByVisibleText(string);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------双击某个元素---------------------------------------------------------
	public void DoubleClick(String locatorExpression,String string){
		WebElement doubleClick;
		try {
			//获取被操作的对象
			doubleClick=driver.findElement(objectMap.getLocator(locatorExpression));
			//声明Actions对象
			Actions action=new Actions(driver);
			//双击元素对象
			action.doubleClick(doubleClick).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 通过页面的Title属性,判断跳转页面的正确性
	 */
	public boolean GetTitle(String string1,String Title){
		String getTitle=driver.getTitle();
		if(getTitle.contains(Title)){
			Log.info("跳转到页面"+getTitle);
			return true;
		}else{
			Log.info("没有跳转到页面"+getTitle);
			return false;
		}
	}
	//------------------------------模拟鼠标事件---------------------------------------------------------
	/*模拟鼠标左键单击和释放的操作*/
	public void LeftMouseClickAndRelease(String locatorExpression,String string){
		try {
			//获取被鼠标操作对象
			WebElement leftmouse=driver.findElement(objectMap.getLocator(locatorExpression));
			//声明Actions对象
			Actions action=new Actions(driver);
			//单击鼠标左键
			action.clickAndHold(leftmouse).perform();
			//暂停2秒释放鼠标左键
			WaitUtil.sleep(2000);
			action.release(leftmouse);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	/*模拟鼠标右键单击操作*/
	public void RightMouseClick(String locatorExpression,String string){
		try {
			//获取被鼠标操作对象
			WebElement rightmouse=driver.findElement(objectMap.getLocator(locatorExpression));
			//声明Actions对象
			Actions action=new Actions(driver);
			//暂停3秒单击鼠标右键
			WaitUtil.sleep(3000);
			action.contextClick(rightmouse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*模拟鼠标悬浮操作*/
	public void OverMouseLink(String locatorExpression,String string){
		//获取被鼠标操作对象
		try {
			WebElement mouselink=driver.findElement(objectMap.getLocator(locatorExpression));
			//声明Actions对象
			Actions action=new Actions(driver);
			//鼠标在超链接上悬浮
			action.moveToElement(mouselink).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------模拟键盘事件---------------------------------------------------------
	public void ClickKey(String locatorExpression,String string){
		//声明Actions对象
		Actions action=new Actions(driver);
		//点击和释放Ctrl键，依次类推
		action.keyDown(Keys.CONTROL);
		action.keyUp(Keys.CANCEL);
	}
	//---------------------------关键字高级用法----------------------------------------------------------------
}
