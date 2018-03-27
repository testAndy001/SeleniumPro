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
	 * ������̬WedDriver�������ڴ�����ص�Driver����
	 * �����洢��λ��������ļ���ObjectMap����
	 * ָ��Log4j�����ļ�ΪLog4j.xml
	 */
	public static WebDriver driver;
	private static ObjectMap objectMap = new ObjectMap(Constants.Path_ConfigurationFile);
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	/*ͨ��browserName�������*/
	@SuppressWarnings("deprecation")
	public static void open_browser(String locatorExpression,String browserName) {
		switch (browserName) {
		case "ie":
			//����������
			System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
			//�趨�̶���IP�ͼ����˿�
			InternetExplorerDriverService.Builder builder = new InternetExplorerDriverService.Builder();
			InternetExplorerDriverService internetExplorerService = builder.usingPort(8080).withHost("127.0.0.1").build();
			//�趨IE�������ʼ��ҳ��
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
			caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.126.com");
			driver = new InternetExplorerDriver(internetExplorerService,caps);
			Log.info("IE�����ʵ���Ѿ�����");
			break;
		case "firefox":
			//����������
			System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			driver = new FirefoxDriver();
			Log.info("��������ʵ���Ѿ�����");
			break;
		case "chrome":
			//����������
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			driver = new ChromeDriver();
			Log.info("Chrome�����ʵ���Ѿ�����");
			break;
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�navigate�ؼ���*/
	public static void navigate(String locatorExpression,String url) {	
		driver.get(url);
		Log.info("�����������ַ" + url);
	}
	/*��ȡ��ǰҳ���url��ַ*/
	public String CurrentPageUrl(String string1,String CurrentPageUrl){
		String GetCurrentPageUrl = null;
		try {
			GetCurrentPageUrl=driver.getCurrentUrl();
			if(CurrentPageUrl.equals(GetCurrentPageUrl)){
				Log.info("��ȡ��ǰҳ��url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GetCurrentPageUrl;
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�input�ؼ���*/
	public static void input(String locatorExpression,String inputString) {
		try {
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("���" + locatorExpression+"������е���������");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("��" + locatorExpression+"�����������"+inputString);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("��" + locatorExpression+"�����������"+inputString+"�����쳣" + e.getMessage());
			e.printStackTrace();
		}

	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�click�ؼ��� */
	public static void click(String locatorExpression,String string) {
		try {
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("����" + locatorExpression+"ҳ��Ԫ�سɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("����" + locatorExpression+"ҳ��Ԫ��ʧ��"+ e.getMessage());
			e.printStackTrace();
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�press_Tab�ؼ��֣����ڰ�Tab���Ĳ���*/
	public static void press_Tab(String locatorExpression,String string) {
		try {
			Thread.sleep(2000);
			// ����KeyBoardUtil��ķ�װ����pressTabKey
			KeyBoardUtil.pressTabKey();
			Log.info("��Tab���ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("��Tab�������쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�press_enter�ؼ��֣� ���ڰ�enter��*/
	public static void press_enter(String locatorExpression,String string) {
		try {
			KeyBoardUtil.pressEnterKey();
			Log.info("��enter���ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("��enter�������쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�paste_string�ؼ���*/
	public static void paste_string(String locatorExpression,String pasteContent) {
		try {
			KeyBoardUtil.setAndCtrlVClipboardData(pasteContent);
			Log.info("�ɹ�����ʼ�����" + pasteContent);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("����ʼ����ĳ����쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�sleep�ؼ���*/
	public static void sleep(String locatorExpression,String sleepTime) {
		try {
			WaitUtil.sleep(Integer.parseInt(sleepTime));
			Log.info("����" + Integer.parseInt(sleepTime) / 1000 + "��ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("�߳����߳����쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�clickButtonList�ؼ���*/
	public static void clickButtonList(String locatorExpression,String string) {
		try {
			List<WebElement> buttons = driver.findElements(objectMap.getLocator(locatorExpression));
			buttons.get(0).click();
			Log.info("���������еİ�ť�ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("���������еİ�ť�����쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}

	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�Assert_String�ؼ���*/
	public static void Assert_String(String locatorExpression,String assertString) {
		try {
			Assert.assertTrue(driver.getPageSource().contains(assertString));
			Log.info("�ɹ����Թؼ���:" + assertString);
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult=false;
			Log.info("���ֶ���ʧ�ܣ������쳣��Ϣ" + e.getMessage());
			System.out.println("����ʧ��");
		}
	}

	/*�˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�close_browser�ؼ��� */
	public static void close_browser(String locatorExpression,String browserName) {
		try {
			System.out.println("������رպ�����ִ��");
			Log.info("�ر����������");
			driver.quit();
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("�ر�����������쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	//---------------------------------------�ؼ��ֻ����÷�-----------------------------------------------
	/*�������*/ 
	public void BrowserWindowsMax(String string1,String string2){
		driver.manage().window().maximize();
	}
	/*����Dimension����,�趨��������ڴ�С*/
	public void SetBrowserWindows(String string1,int[] num){
		Dimension dimesion=new Dimension(num[0],num[1]);
		driver.manage().window().setSize(dimesion);
	}
	/*ģ�������ˢ�µ�ǰҳ��*/
	public void FreshCurrentPage(String string1,String string2){
		driver.navigate().refresh();
	}
	/*ģ���������ǰ������*/
	public void ForwardCurrentPage(String string1,String string2){
		driver.navigate().forward();
	}
	/*ģ��������ĺ��˹���*/
	public void BackCurrentPage(String string1,String string2){
		driver.navigate().back();
	}
	/*ʶ��Ͳ����µ��������������*/
	public static void OpenLinkWindow(String locatorExpression,String string){
		//����ǰ��������ڵľ���洢��parentWindowHandle������
		String parentWindowHandle=driver.getWindowHandle();
		//�ҵ�ҳ����Ψһ������Ԫ�أ��洢��link������
		WebElement link;
		try {
			link = driver.findElement(objectMap.getLocator(locatorExpression));
			//��������
			link.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//��ȡ��ǰ���д򿪴��ڵľ�����洢��һ��set������
		Set<String> allWindowHandles=driver.getWindowHandles();
		//�洢����Ϊ�գ����������е����ж���
		if(!allWindowHandles.isEmpty()){
			for(String allWindowHandle:allWindowHandles){
				//��ȡҳ��ı������ԣ��ж�����ֵ�Ƿ�string�����ؼ���
				String title=driver.switchTo().window(allWindowHandle).getTitle();
				if(title.equals(string)){
					Log.info("�жϳ���"+string);
					break;
				}else{
					Log.info("�жϲ�����"+string);
					break;
				}
			}
		}
		//�����ʼ�򿪵������ҳ��
		driver.switchTo().window(parentWindowHandle);
	}
	/*����ǰ��������ڽ���*/
	public void captureScreenInCurrentWindow(String locatorExpression,String string){
		//�ѵ�ǰ�������ҳ����н�ͼ�����浽File������
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			//��File�����ļ�ת����һ��png�ļ������浽c����
			FileUtils.copyFile(srcFile,new File("c:\\Ŀ¼��\\string.png"));
			Log.info("����ͼƬ�ļ��ɹ�");
		} catch (IOException e) {
			Log.info("����ͼƬ�ļ�ʧ��");
			e.printStackTrace();
		}
	}
	/*�ر����������*/
	public void killWindowProcess(String locatorExpression,String string){
		//�ر���������г���
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
	//----------------------------�л�������У����������Ԫ��------------------------------------------------
	public static void switchTo_frame(String locatorExpression,String string){
		try{
			//��ȡǶ��ʽ��ܶ��󣬽���������
			WebElement iframe=driver.findElement(objectMap.getLocator(locatorExpression));
			driver.switchTo().frame(iframe);
			System.out.println("�ڿ����");
			Log.info("����"+locatorExpression+"��ܳɹ�");
		}catch(Exception e){
			TestSuiteByExcel.testResult=false;
			Log.info("����"+locatorExpression+"���ʧ��");
			e.printStackTrace();
		}
	}
	//----------------------����javaScript��confirm/prompt����-------------------------------------------
	public static void handleconfirm(String locatorExpression,String string){
		try{
			//��ȡAlert����
			Alert alert=driver.switchTo().alert();
			//����ȷ����ť
			alert.accept();
			//����ȡ����ť
			//alert.dismiss();
		}catch(Exception e){
			TestSuiteByExcel.testResult=false;
			Log.info("���Բ�����ʧ��");
			e.printStackTrace();
		}
	}
	//----------------------------����������е�Cookie--------------------------------------------------
	public static void handleCookie(String locatorExpression,String string){
		Set<Cookie>cookies=driver.manage().getCookies();
		for(Cookie cookie:cookies){
			System.out.println("���ڵ���:"+cookie.getDomain());
			System.out.println("name:"+cookie.getName());
			System.out.println("value:"+cookie.getValue());
			System.out.println("��Ч����:"+cookie.getExpiry());
			System.out.println("·��:"+cookie.getPath());
			//ͨ������,ɾ��Cookie
			Cookie newCookie=new Cookie(cookie.getName(),cookie.getValue());
			driver.manage().deleteCookie(newCookie);		
		}
		/*
		 * ɾ��Cookie����1ͨ������
		 * driver.manage().deleteCookieNamed("");
		 * ɾ��Cookie����3ɾ������
		 * driver.manage().deleteAllCookies();
		 */
		try {
			Thread.sleep(1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//----------------------------------�ж�ҳ��Ԫ���Ƿ����---------------------------------------------
	public static boolean IfElementExist(String locatorExpression,WebElement string) {
		try {
			string=driver.findElement(objectMap.getLocator(locatorExpression));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(string!=null){
			Log.info("������Ԫ���ҵ�");
			return true;
		}else{
			Log.info("������Ԫ��û���ҵ�");
			return false;
		}
	}
	//---------------------------���ҳ��Ԫ�ص��ı������Ƿ����------------------------------------------------
	public void IfWebElementText(String locatorExpression,String string){
		//�������
		WebElement text;
		try {
			text = driver.findElement(objectMap.getLocator(locatorExpression));
			String contentText=text.getText();
			//��ȫƥ��
			Assert.assertEquals(contentText, string);
			//���������Ƿ����String
			Assert.assertTrue(contentText.contains(string));
			//�������ݿ�ʼ�����Ƿ���String
			Assert.assertTrue(contentText.startsWith(string));
			//�������ݽ��������Ƿ���String
			Assert.assertTrue(contentText.endsWith(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//---------------------------��ʾ�ȴ�ҳ��Ԫ���Ƿ������ҳ����-------------------------------------------------------------
	/* �˷��������ƶ�ӦExcel�ļ����ؼ��֡����е�WaitFor_Element�ؼ���*/
	public static void WaitFor_Element(String locatorExpression,String string) {

		try {
			// ���÷�װ��waitWebElement������ʾ�ȴ�ҳ��Ԫ���Ƿ����
			WaitUtil.waitWebElement(driver, locatorExpression);
			Log.info("��ʾ�ȴ�Ԫ�س��ֳɹ���Ԫ����" +locatorExpression);
		} catch (Exception e) {
			TestSuiteByExcel.testResult=false;
			Log.info("��ʾ�ȴ�Ԫ�س����쳣�������쳣��Ϣ" + e.getMessage());
			e.printStackTrace();
		}
	}
	//---------------------------��鵥ѡ�б��ѡ�������Ƿ��������----------------------------------------------
	public void CheckSlectText(String locatorExpression,String[] string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//�趨��ѡ�б������ֵ
			List<String>expect_option=new ArrayList<String>();
			expect_option.add(string.toString());
			//��ȡ��ѡ�б��ʵ��ֵ�洢��list�б���
			List<String>actual_option=new ArrayList<String>();
			for(WebElement option:dropList.getOptions()){
				actual_option.add(option.getText());
				//�ж�����ֵ�Ƿ���ʵ��ֵһ��
				if(expect_option.toArray().equals(actual_option.toArray())){
					Log.info("����ֵ��ʵ��ֵһ��"+expect_option.toArray());
					break;
				}else{
					Log.info("����ֵ��ʵ��ֵ��һ��"+actual_option.toArray());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	//-------------------------------������ѡ��--------------------------------------------------------
	/*ѡ�и�ѡ��*/
	public void SelectCheckBox(String locatorExpression,String string){
		//��ȡ��ѡ��Ԫ�ض���
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(locatorExpression));
			if(!checkbox.isSelected()){
				checkbox.click();
				Log.info("ѡ�и�ѡ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*ȡ����ѡ��*/
	public void NoSelectCheckBox(String locatorExpression,String string){
		//��ȡ��ѡ��Ԫ�ض���
		WebElement checkbox;
		try {
			checkbox = driver.findElement(objectMap.getLocator(locatorExpression));
			if(checkbox.isSelected()){
				checkbox.click();
				Log.info("ȡ��ѡ�и�ѡ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*ѡ��ȫ����ѡ��*/
	public void AllSelectCheckBox(String locatorExpression,String string){
		List<WebElement> checkboxs;
		try {
			checkboxs = driver.findElements(objectMap.getLocator(locatorExpression));
			for(WebElement checkbox:checkboxs ){
				checkbox.click();
				//checkbox.click();ȡ��ȫ��ѡ�еĸ�ѡ��
				Log.info("ȫ��ѡ�и�ѡ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//--------------------------------������ѡ��-------------------------------------------------------
	/*ѡ�е�ѡ��*/
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
	/*ȡ����ѡ��*/
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
	/*ѡ��ȫ����ѡ��*/
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
	//--------------------------������ѡ��ѡ���б�----------------------------------------------------------
	/*ͨ��ѡ���Value����ֵѡ���б�*/
	public void DropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//�ж������б��Ƿ�֧�ֶ�ѡ,֧�ֶ�ѡ����true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.selectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*ͨ��ѡ���Value����ֵȡ��ѡ���б�*/
	public void NoDropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			//�ж������б��Ƿ�֧�ֶ�ѡ,֧�ֶ�ѡ����true
			boolean flag=dropList.isMultiple();
			if(flag==true){
				dropList.deselectByValue(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//---------------------------------������ѡ�����б�---------------------------------------------------
	/*ͨ��ѡ���б��valueֵ������ѡ�в���*/
	public void OneDropList(String locatorExpression,String string){
		try {
			Select dropList=new Select(driver.findElement(objectMap.getLocator(locatorExpression)));
			boolean flag=dropList.isMultiple();
			if(flag==false){
				Log.info("���뵥ѡ�����б�");
				dropList.selectByValue(string);
				/* ͨ��ѡ���б���±�ֵ����ѡ��
				 * dropList.selectByIndex(0);
				 * ͨ��ѡ������ֽ���ѡ��
				 * dropList.selectByVisibleText(string);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------˫��ĳ��Ԫ��---------------------------------------------------------
	public void DoubleClick(String locatorExpression,String string){
		WebElement doubleClick;
		try {
			//��ȡ�������Ķ���
			doubleClick=driver.findElement(objectMap.getLocator(locatorExpression));
			//����Actions����
			Actions action=new Actions(driver);
			//˫��Ԫ�ض���
			action.doubleClick(doubleClick).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * ͨ��ҳ���Title����,�ж���תҳ�����ȷ��
	 */
	public boolean GetTitle(String string1,String Title){
		String getTitle=driver.getTitle();
		if(getTitle.contains(Title)){
			Log.info("��ת��ҳ��"+getTitle);
			return true;
		}else{
			Log.info("û����ת��ҳ��"+getTitle);
			return false;
		}
	}
	//------------------------------ģ������¼�---------------------------------------------------------
	/*ģ���������������ͷŵĲ���*/
	public void LeftMouseClickAndRelease(String locatorExpression,String string){
		try {
			//��ȡ������������
			WebElement leftmouse=driver.findElement(objectMap.getLocator(locatorExpression));
			//����Actions����
			Actions action=new Actions(driver);
			//����������
			action.clickAndHold(leftmouse).perform();
			//��ͣ2���ͷ�������
			WaitUtil.sleep(2000);
			action.release(leftmouse);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	/*ģ������Ҽ���������*/
	public void RightMouseClick(String locatorExpression,String string){
		try {
			//��ȡ������������
			WebElement rightmouse=driver.findElement(objectMap.getLocator(locatorExpression));
			//����Actions����
			Actions action=new Actions(driver);
			//��ͣ3�뵥������Ҽ�
			WaitUtil.sleep(3000);
			action.contextClick(rightmouse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*ģ�������������*/
	public void OverMouseLink(String locatorExpression,String string){
		//��ȡ������������
		try {
			WebElement mouselink=driver.findElement(objectMap.getLocator(locatorExpression));
			//����Actions����
			Actions action=new Actions(driver);
			//����ڳ�����������
			action.moveToElement(mouselink).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------ģ������¼�---------------------------------------------------------
	public void ClickKey(String locatorExpression,String string){
		//����Actions����
		Actions action=new Actions(driver);
		//������ͷ�Ctrl������������
		action.keyDown(Keys.CONTROL);
		action.keyUp(Keys.CANCEL);
	}
	//---------------------------�ؼ��ָ߼��÷�----------------------------------------------------------------
}
