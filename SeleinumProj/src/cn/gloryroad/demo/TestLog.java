package cn.gloryroad.demo;

import org.apache.log4j.xml.DOMConfigurator;

import cn.gloryroad.util.Log;

public class TestLog {

	public static void main(String[] args) {
		/*
		 * ���й�����Log�ķ���
		 */
		DOMConfigurator.configure("log4j.xml");
		Log.startTestCase("��ʼ����");
		Log.info("���в���");
		Log.error("����ʧ��");

	}

}
