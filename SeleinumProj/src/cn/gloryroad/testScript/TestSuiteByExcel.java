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
 *ִ�в��Լ��ϵĲ����� 
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
		// ����һ���ؼ��������ʵ��
		keyWordsaction = new KeyWordsAction();
		// ʹ��java�ķ�����ƻ�ȡ KeyWordsAction������з�������
		method = keyWordsaction.getClass().getMethods();
		// ����Excel�ؼ��ļ���·��
		String excelFilePath = Constants.Path_ExcelFile;
		// �趨��ȡExcel�ļ��еġ������ʼ���sheetΪ����Ŀ��
		ExcelUtil.setExcelFile(excelFilePath,Constants.Sheet_TestSteps);
		// ��ȡ�������������ϡ�sheet�еĲ�����������
		int testCasesCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);
		// ʹ��forѭ����ִ�����б��Ϊ��y���Ĳ�������
		for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {
			// ��ȡ�������������ϡ�sheet��ÿ�еĲ����������
			testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_TestCaseID);
			// ��ȡ�������������ϡ�sheet�е�������ÿ�е�ֵ���ж��Ƿ�ִ�в����׼�
			testCaseRunFlag=ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo,Constants.Col_RunFlag);
			//����������е�ֵΪ��y������ִ�в��������е����в���
			if(testCaseRunFlag.equalsIgnoreCase("y")){
				//����־�д�ӡ����������ʼִ��
				Log.startTestCase(testCaseID);
				//�趨���������ĵ�ǰ���Ϊtrue,���������ִ�гɹ�
				testResult=true;
				//�ڡ������ʼ���sheet�У���ȡ��ǰҪִ�в��������ĵ�һ�������������к�
				testStep=ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps, testCaseID, Constants.Col_TestCaseID);
				//�ڡ������ʼ���sheet�У���ȡ��ǰҪִ�в�����������ĸ���(2����5)
				testLastStep=ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, testCaseID, testStep);
				//�������������е����в��Բ���,��Ҫ�����ж����������޸Ĵ˺���
				int count=testStep+testLastStep;
				for(;testStep<count;testStep++){
					System.out.println("ִ�е�:"+testStep+"����");
					//�ӡ������ʼ���sheet�ж�ȡ�ؼ��֡�Ԫ�ض�λ���ʽ������ֵ��������execute_Actions����ִ��
					//����־�ļ��д�ӡ�ؼ�����Ϣ
					keyword=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_KeyWordAction);
					Log.info("��excel�ļ��ж�ȡ���Ĺؼ�����:"+keyword);
					//����־�ļ��д�ӡԪ�ض�λ���ʽ��Ϣ
					locatorExpression=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep, Constants.Col_locatorExpression);
					Log.info("��excel�ļ��ж�ȡ����Ԫ�ض�λ���ʽ��:"+locatorExpression);
					//����־�ļ��д�ӡ����ֵ��Ϣ
					value=ExcelUtil.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_ActionValue);
					Log.info("��excel�ļ��ж�ȡ���Ĳ���ֵ��:"+value);
					execute_Actions();
					if(testResult==true){
						//���������κ�һ�����Բ���ִ�гɹ���ִ�н��������ִ�гɹ���
						ExcelUtil.setCellData("������������",testCaseNo,Constants.Col_TestSuiteTestReust,"����ִ�гɹ�");
					}
					if(testResult==false){
						//���������κ�һ�����Բ���ִ��ʧ�ܣ�ִ�н��������ִ��ʧ�ܡ�
						ExcelUtil.setCellData("������������",testCaseNo,Constants.Col_TestSuiteTestReust,"����ִ��ʧ��");
						//����־�д�ӡ��������ִ�����
						Log.endTestCase(testCaseID);
						//��ǰ��������ִ��ʧ�ܣ����������������趨Ϊʧ��״̬
						//break���������ǰforѭ��������ִ�в��Լ����е���һ������
						break;
					}
				}
				//����־�д�ӡ��������ִ�����
				Log.endTestCase(testCaseID);
			}
			
			
		}

	}
	private static void execute_Actions(){
		try{
			//����������
			for(int i=0;i<method.length;i++){
				/*
				 * ʹ�÷���ķ�ʽ���ҵ��ؼ��ֶ�Ӧ�Ĳ��Է�������ʹ��locatorExpression(���ʽ)��value(����ֵ)
				 * ��Ϊ���Է����ĺ���ֵ���е���
				 */
				if(method[i].getName().equals(keyword)){
					//������
					System.out.println(method[i].getName()+":"+keyword+":"+value);
					method[i].invoke(keyWordsaction,locatorExpression,value);
					if(testResult==true){
						/*��ǰ���Բ���ִ�гɹ����ڡ������ʼ���sheet���У��Ὣ��ǰִ�еĲ��Բ���
						 * ����趨Ϊ�����Բ���ִ�гɹ���
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_TestStepTestReust,"���Բ���ִ�гɹ�");
						break;
					} 
					if(testResult==false){
						/*��ǰ���Բ���ִ��ʧ�ܣ��ڡ������ʼ���sheet���У��Ὣ��ǰִ�еĲ��Բ���
						 * ����趨Ϊ�����Բ���ִ��ʧ�ܡ�
						 */
						ExcelUtil.setCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_TestStepTestReust,"���Բ���ִ��ʧ��");
						//���Բ���ִ��ʧ�ܣ��ر������������ִ�к������Բ���
						KeyWordsAction.close_browser("","");
						break;
					}
				}
			}
		}catch(Exception e){
			//���ò��Է��������У��������쳣���򽫲����趨Ϊʧ��״̬��ֹͣ��������ִ��
			Assert.fail("ִ�г�ϯ�쳣����������ִ��ʧ��");
		}
	}
	@BeforeClass
	public void BeforeClass(){
		//��ȡLog4j�������ļ�log4j.xml(���������־)
		DOMConfigurator.configure("log4j.xml");
	}
}
