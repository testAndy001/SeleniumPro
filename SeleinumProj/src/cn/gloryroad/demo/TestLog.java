package cn.gloryroad.demo;

import org.apache.log4j.xml.DOMConfigurator;

import cn.gloryroad.util.Log;

public class TestLog {

	public static void main(String[] args) {
		/*
		 * 运行工具类Log的方法
		 */
		DOMConfigurator.configure("log4j.xml");
		Log.startTestCase("开始测试");
		Log.info("运行测试");
		Log.error("测试失败");

	}

}
