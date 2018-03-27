package cn.gloryroad.demo;

import cn.gloryroad.util.KeyBoardUtil;

public class TestKeyBoardUtil {

	public static void main(String[] args) throws Exception {
		//测试复制粘贴组合键功能，在指定光标位置，显示组合键复制粘贴的内容
		KeyBoardUtil.setAndCtrlVClipboardData("nihao");
	}

}
