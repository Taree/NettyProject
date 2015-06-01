package com.baifendian.bean;

import java.util.Date;

public class News {
     private String title;
     private Date time;
     private String source;
     private String link;
     private String summary;
     private String content;
     public News(){
    	 super();
     }
     public News(String title,String content){
        this.title=title;
        this.content=content;
     }
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "title: "+title+"   content: "+content;
	}
     
     
     
}
