package cn.gloryroad.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.gloryroad.configuration.Constants;
import cn.gloryroad.testScript.TestSuiteByExcel;

//������Ҫʵ��Excel�ļ�����
public class ExcelUtil {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow 	Row;
	
	// �趨Ҫ������Excel�ļ�·����Excel�ļ��е�sheet����
	// �ڶ�/д�ļ���ʱ�򣬾���Ҫ�ȵ��ô˷������趨Ҫ������Excel�ļ�·����Excel�ļ��е�sheet����
	public static void setExcelFile(String Path, String SheetName) throws Exception {

		FileInputStream ExcelFile;
		try {
			// ʵ����Excel�ļ���FileInputStream�Ķ���
			ExcelFile = new FileInputStream(Path);
			// ʵ����Excel�ļ���XSSFworkbook�ļ�����
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			// ʵ����XSSFSheet����ָ��Excel�ļ���sheet���ƣ���������Sheet���С��к͵�Ԫ��Ĳ���
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw (e);
		}

	}
	// ��ȡExcel�ļ������һ�е��к�
	public static int getLastRowNum() {
		// ��������Sheet�����һ�е��к�
		return ExcelWSheet.getLastRowNum();
	}
	// ��ȡָ��sheet�е�ָ����Ԫ�������˺���ֻ֧����չ��Ϊ.xlsx��excel�ļ�
	public static String getCellData(String SheetName, int RowNum, int ColNum) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// ͨ����������ָ����Ԫ����кź��кţ���ȡָ���ĵ�Ԫ�����
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			/*
			 *�����Ԫ�������Ϊ�ַ������ͣ���ʹ��getStringCellValue
			 *�����Ԫ�������Ϊ���������ͣ���ʹ��getNumericCellValue
			 * ͨ��java����Ԫ�������ȡ�ĵ�Ԫ�����������Ƿ����ַ������ǡ�ֱ�ӷ����ַ�����������������ת�����ַ��������ַ�����
			 * Cell.getCellType()�� XSSFCell.CELL_TYPE_STRING��������߱�ʾ������ʹ�õķ���(������ʱ)
			 */
			//��ʾ������ʱ������������
			@SuppressWarnings("deprecation")
			String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell.getStringCellValue()
					: String.valueOf(Math.round(Cell.getNumericCellValue()));
			return CellData;
		} catch (Exception e) {
			e.printStackTrace();
			// ��ȡ����򷵻ؿ��ַ���
			return "������";
		}
	}
	/*
	 * �����������ķ���
	 */
	//  �趨Ҫ������Excel�ļ�·�����ڶ�дexcel�ļ���ʱ����Ҫ���趨Ҫ������Excel�ļ�·��
	public static void setExcelFile(String Path) {
		FileInputStream ExcelFile;

		try {
			// ʵ����excel��FileInputStream����
			ExcelFile = new FileInputStream(Path);
			// ʵ����excel��XSSFWorkbook����
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("excel·���趨ʧ��");
			e.printStackTrace();
		}

	}
	// ��ȡָ��sheet�е������ܵ�����
	public static int getRowCount(String SheetName) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		//��ȡ�����������⣩���ݵ����һ���к�
		int number = ExcelWSheet.getLastRowNum();
		return number;

	}

	// ��Excelָ����sheet�У���ȡ��һ�ΰ���ָ����������������ֵ��к�
	public static int getFirstRowContainsTestCaseID(String SheetName, String testCaseName, int colNum) {
		int j = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtil.getRowCount(SheetName);
			for (int i = 0; i <=rowCount; i++) {
				// ʹ��ѭ���ķ�������������������е������У��ж��Ƿ����ĳ������������Źؼ���
				if (ExcelUtil.getCellData(SheetName, i, colNum).equalsIgnoreCase(testCaseName)) {
					// ������������˳�forѭ���������ذ�������������Źؼ��ֵ��к�
					j=i;
					break;
				}
			}
			return j;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			return 0;
		}
	}

	// ��ȡָ��sheet��ĳ��������������ĸ���
	public static int getTestCaseLastStepRow(String SheetName, String testCaseID, int testCaseStartRowNumber) {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			/*
			 * �Ӱ���ָ������������ŵĵ�һ�п�ʼ���б�����ֱ��ĳһ�в�����ָ������������� ��ʱ�ı����������ǲ�����������ĸ���
			 */
			int number=0;
			for (int i=testCaseStartRowNumber; i<=ExcelUtil.getRowCount(SheetName); i++) {
				boolean flag=testCaseID.equals(ExcelUtil.getCellData(SheetName,i, Constants.Col_TestCaseID));
				if (flag==true) {
					number++;
					continue;
				}else if(!flag){
					break;
				}
			}
			return number;
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			return 0;
		}
	}

	// ��Excel�ļ���ִ�е�Ԫ����д�����ݣ��˺���ֻ֧����չ��Ϊxlsx��excel�ļ�д��
	public static void setCellData(String SheetName, int RowNum, int ColNum, String Result) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// ��ȡexcel�ļ��е��ж���
			Row = ExcelWSheet.getRow(RowNum);
			// �����Ԫ��Ϊ�գ��򷵻�null
			Cell = Row.getCell(ColNum);
			if (Cell == null) {
				// ����Ԫ����null,������Ԫ��
				// �����Ԫ��Ϊ�գ��޷�ֱ�ӵ��õ�Ԫ������setCellValue�����趨��Ԫ���ֵ
				Cell = Row.createCell(ColNum);
				// ������Ԫ�������Ե��õ�Ԫ������setCellValue�����趨��Ԫ���ֵ
				Cell.setCellValue(Result);
			} else {
				// ��Ԫ���������ݣ�����ֱ�ӵ��õ�Ԫ�����setCellValue�����趨��Ԫ���ֵ
				Cell.setCellValue(Result);
			}
			// ʵ����д��excel�ļ����ļ����������
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_ExcelFile);
			// ������д��excel�ļ���
			ExcelWBook.write(fileOut);
			// ����flsuh����ǿ��ˢ��д���ļ�
			fileOut.flush();
			// �ر��ļ����������
			fileOut.close();
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}

}
