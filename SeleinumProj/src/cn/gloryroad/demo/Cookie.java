package cn.gloryroad.demo;

import cn.gloryroad.configuration.KeyWordsAction;

public class Cookie {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyWordsAction.open_browser("","ie");
		KeyWordsAction.navigate("","http://firefox.com");
		KeyWordsAction.handleCookie("","");

	}

}
