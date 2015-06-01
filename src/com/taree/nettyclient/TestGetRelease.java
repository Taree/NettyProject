package com.taree.nettyclient;

import java.util.ArrayList;

import com.baifendian.bean.KeywordBean;

public class TestGetRelease {
     public static void crawl(ArrayList<KeywordBean> list){
    	 
    	 for(int i=0;i<list.size();i++){
    		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			KeywordBean k=list.get(i);
			System.out.println(k.getKeyword()); 
			k.setSessionId(2);
			   NettyClient.getInstance().getChannel().write(k);
    	 }
     }
}
