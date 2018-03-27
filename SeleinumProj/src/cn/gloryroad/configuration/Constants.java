package cn.gloryroad.configuration;

public class Constants {
	//测试数据相关常量设定
	public static final String Path_ExcelFile="F:\\workspace\\SeleinumProj\\src\\cn\\gloryroad\\data\\关键字驱动测试用例.xlsx";
	public static final String Path_ConfigurationFile="F:\\workspace\\SeleinumProj\\objectMap.properties";
	//测试数据sheet中的列号常量设定
	//第一列用0表示，测试用例序号列
	public static final int Col_TestCaseID=0;
	//第四列用3表示，关键字列
	public static final int Col_KeyWordAction=3;
	//第五列用4表示，操作元素的定位表达式列
	public static final int Col_locatorExpression=4;
	//第六列用5表示,操作值列
	public static final int Col_ActionValue=5;
	//第七列用6表示，测试结果列
	public static final int Col_TestStepTestReust=6;
	//测试集合sheet中的执行列号常量设定，测试用例执行参数列
	public static final int Col_RunFlag=2;
	//测试集合sheet中的结果列号常量设定，测试用例执行结果列
	public static final int Col_TestSuiteTestReust=3;
	//测试用例sheet名称的常量设定
	public static final String Sheet_TestSteps="发送邮件";
	//测试用例集sheet名称的常量设定
	public static final String Sheet_TestSuite="测试用例集合";
}
