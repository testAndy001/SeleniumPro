package cn.gloryroad.testScript;

import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.junit.BeforeClass;
import cn.gloryroad.configuration.Constants;
import cn.gloryroad.configuration.KeyWordsAction;
import cn.gloryroad.util.ExcelUtil;
import cn.gloryroad.util.Log;

/*
 *执行测试集合的测试类 
 */
public class TestSuiteByExcel {

	public static Method method[];
	public static String keyword;
	public static String value;
	public static String locatorExpression;
	public static KeyWordsAction keyWordsaction;
	public static int testStep;
	public static int testLastStep;
	public static int getLastRowNum;
	public static String testCaseID;
	public static String testCaseRunFlag;
	public static boolean testResult;
	@Test
	public void testTestSuite() throws Exception {
		// 声明一个关键动作类的实例
		keyWordsaction = new KeyWordsAction();
		// 使用java的反射机制获取 KeyWordsAction类的所有方法对象
		method = keyWordsaction.getClass().getMethods();
		// 定义Excel关键文件的路径
		String excelFilePath = Constants.Path_ExcelFile;
		// 设定读取Excel文件中的“发送邮件”sheet为操作目标
		ExcelUtil.setExcelFile(excelFilePath,Constants.Sheet_TestSteps);
		// 读取“测试用例集合”sheet中的测试用例总数
		int testCasesCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);
		// 使用for循环，执行所有标记为“y”的测试用例
		for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {
			// 读取“测试用例集合”sheet中每行的测试用例序号
			testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_TestCaseID);
			// 读取“测试用例集合”sheet中第三列中每行的值，判断是否执行测试套件
			testCaseRunFlag=ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo,Constants.Col_RunFlag);
			//如果运行列中的值为“y”，则执行测试用例中的所有步骤
			if(testCaseRunFlag.equalsIgnoreCase("y")){
				//在日志中打印测试用例开始执行
				Log.startTestCase(testCaseID);
				//设定测试用例的当前结果为true,则表明测试执行成功
				testResult=true;
				//在“发送邮件”sheet中，获取当前要执行测试用例的第一个步骤所在行行号
				testStep=ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps, testCaseID, Constants.Col_TestCaseID);
				//在“发送邮件”sheet中，获取当前要执行测试用例步骤的个数(2或者5)
				testLastStep=ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, testCaseID, testStep);
				//遍历测试用例中的所有测试步骤,需要加入判断条件，来修改此函数
				int count=testStep+testLastStep;
				for(;testStep<count;testStep++){
					System.out.println("执行第:"+testStep+"步骤");
					//从“发送邮件”sheet中读取关键字、元素定位表达式、操作值，并调用execute_Actions方法执行
					//在日志文件中打印关键字信息
					keyword=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_KeyWordAction);
					Log.info("从excel文件中读取到的关键字是:"+keyword);
					//在日志文件中打印元素定位表达式信息
					locatorExpression=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep, Constants.Col_locatorExpression);
					Log.info("从excel文件中读取到的元素定位表达式是:"+locatorExpression);
					//在日志文件中打印操作值信息
					value=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_ActionValue);
					Log.info("从excel文件中读取到的操作值是:"+value);
					execute_Actions();
					if(testResult==true){
						//测试用例任何一个测试步骤执行成功，执行结果“测试执行成功”
						ExcelUtil.setCellData("测试用例集合",testCaseNo,Constants.Col_TestSuiteTestReust,"测试执行成功");
					}
					if(testResult==false){
						//测试用例任何一个测试步骤执行失败，执行结果“测试执行失败”
						ExcelUtil.setCellData("测试用例集合",testCaseNo,Constants.Col_TestSuiteTestReust,"测试执行失败");
						//在日志中打印测试用例执行完毕
						Log.endTestCase(testCaseID);
						//当前测试用例执行失败，则整个测试用例设定为失败状态
						//break语句跳出当前for循环，继续执行测试集合中的下一个测试
						break;
					}
				}
				//在日志中打印测试用例执行完毕
				Log.endTestCase(testCaseID);
			}
			
			
		}

	}
	private static void execute_Actions(){
		try{
			//数组有问题
			for(int i=0;i<method.length;i++){
				/*
				 * 使用反射的方式，找到关键字对应的测试方法，并使用locatorExpression(表达式)和value(操作值)
				 * 作为测试方法的函数值进行调用
				 */
				if(method[i].getName().equals(keyword)){
					//有问题
					System.out.println(method[i].getName()+":"+keyword+":"+value);
					method[i].invoke(keyWordsaction,locatorExpression,value);
					if(testResult==true){
						/*当前测试步骤执行成功，在“发送邮件”sheet表中，会将当前执行的测试步骤
						 * 结果设定为“测试步骤执行成功”
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_TestStepTestReust,"测试步骤执行成功");
						break;
					} 
					if(testResult==false){
						/*当前测试步骤执行失败，在“发送邮件”sheet表中，会将当前执行的测试步骤
						 * 结果设定为“测试步骤执行失败”
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_TestStepTestReust,"测试步骤执行失败");
						//测试步骤执行失败，关闭浏览器，不再执行后续测试步骤
						KeyWordsAction.close_browser("","");
						break;
					}
				}
			}
		}catch(Exception e){
			//调用测试方法过程中，若出现异常，则将测试设定为失败状态，停止测试用例执行
			Assert.fail("执行出席异常，测试用例执行失败");
		}
	}
	@BeforeClass
	public void BeforeClass(){
		//读取Log4j的配置文件log4j.xml(正常输出日志)
		DOMConfigurator.configure("log4j.xml");
	}
}
