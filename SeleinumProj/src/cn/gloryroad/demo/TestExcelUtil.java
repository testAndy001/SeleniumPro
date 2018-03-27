package cn.gloryroad.demo;

import cn.gloryroad.util.ExcelUtil;

public class TestExcelUtil {

	public static void main(String[] args) {
		/* 测试工具类ExcelUtil中的方法，使用Excel表格 */
		// 获取Excel文件地址
		ExcelUtil.setExcelFile("E:\\testExcel.xlsx");
		//获取第一次包含指定测试用例序号文字的行号
		int num=ExcelUtil.getFirstRowContainsTestCaseID("TestCase", "test_3", 0);
		System.out.println("初次出现《test_3》的数据行行号："+num);
		// 获取指定sheet中某个测试用例步骤的个数
		int stepNum1 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_1", num);
		int stepNum2 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_2", num);
		int stepNum3 = ExcelUtil.getTestCaseLastStepRow("TestCase", "test_3", num);
		System.out.println("test_1用例测试步骤总数：" + stepNum1);
		System.out.println("test_2用例测试步骤总数：" + stepNum2);
		System.out.println("test_3用例测试步骤总数：" + stepNum3);
		//创建单元格
		ExcelUtil.setCellData("TestCase",0,3,"执行结果");

	}

}
