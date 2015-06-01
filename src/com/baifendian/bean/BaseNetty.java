package com.baifendian.bean;

import java.io.Serializable;

public class BaseNetty implements Serializable {
	private static final long serialVersionUID = -8443519314107050709L;
	private int sessionId;  //调用方法标识
	private int top; //任务数
	

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}