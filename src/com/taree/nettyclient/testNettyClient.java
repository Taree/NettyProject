package com.taree.nettyclient;

import com.baifendian.bean.KeywordBean;
import com.baifendian.bean.Site;

public class testNettyClient {
   public static void main(String[] args) {
	   
	   KeywordBean keywordBean=new KeywordBean();
//	   keywordBean.setId(1);
	   keywordBean.setSessionId(1);
	   keywordBean.setTop(5);
//	   Site site=new Site();
//	   site.setId(1111);
	   NettyClient.getInstance().startService();
	   //报ailed to read class descriptor
	   NettyClient.getInstance().getChannel().write(keywordBean);
	   
//	   NettyClient.getInstance().getChannel().write(11);
   }
}
