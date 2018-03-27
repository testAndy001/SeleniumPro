package cn.gloryroad.demo;

import cn.gloryroad.util.WaitUtil;

public class TestWaitUtil {
	
	public void start(){
		System.out.println("延迟3秒");
	}
	public static void main(String[] args) {
		/*
		 * 测试工具类WaitUtil的方法
		 */
		for(int i=0;i<10;i++){
			WaitUtil.sleep(3000);
			new TestWaitUtil().start();
			System.out.println(i);
		}
	}

}
