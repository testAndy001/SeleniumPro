package cn.gloryroad.demo;

import cn.gloryroad.util.ExcelUtil;

public class TestExcelUtil {

	public static void main(String[] args) {
		/* ���Թ�����ExcelUtil�еķ�����ʹ��Excel��� */
		// ��ȡExcel�ļ���ַ
		ExcelUtil.setExcelFile("E:\\testExcel.xlsx");
		//��ȡ��һ�ΰ���ָ����������������ֵ��к�
		int num=ExcelUtil.getFirstRowContainsTestCaseID("TestCase", "test_3", 0);
		System.out.println("���γ��֡�test_3�����������кţ�"+num);
		// ��ȡָ��sheet��ĳ��������������ĸ���
		int stepNum1 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_1", num);
		int stepNum2 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_2", num);
		int stepNum3 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_3", num);
		System.out.println("test_1�������Բ���������" + stepNum1);
		System.out.println("test_2�������Բ���������" + stepNum2);
		System.out.println("test_3�������Բ���������" + stepNum3);
		//������Ԫ��
		ExcelUtil.setCellData("TestCase",0,3,"ִ�н��");

	}

}
