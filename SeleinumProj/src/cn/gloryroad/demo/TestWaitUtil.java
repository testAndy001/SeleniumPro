package cn.gloryroad.demo;

import cn.gloryroad.util.WaitUtil;

public class TestWaitUtil {
	
	public void start(){
		System.out.println("�ӳ�3��");
	}
	public static void main(String[] args) {
		/*
		 * ���Թ�����WaitUtil�ķ���
		 */
		for(int i=0;i<10;i++){
			WaitUtil.sleep(3000);
			new TestWaitUtil().start();
			System.out.println(i);
		}
	}

}
