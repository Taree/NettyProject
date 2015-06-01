package com.baifendian.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by BFD_300 on 2015/5/24.
 * 抓取程序调用时最主要的是link，和lastCrawlSign，
 * 返回实体时最主要填充字段为lastCrawlTime，nextCrawlTime。
 * 
 */

public class KeywordBean2 implements Serializable{
	private static final long serialVersionUID = -8443519314107050709L;
    private int id;

    private String sid;//来源
    
    private String cate;//品类，可认为是一级标题
    
    private String name;//品牌，可认为是二级标题
    
    private String keyword;//关键词
    
    private String link;//抓取链接
    
    private Date lastCrawlTime;//上次抓取时间
    
    private Date nextCrawlTime;//下次抓取时间
    
    private int Crawl_level;//抓取的等级
	
    private int status;//状态，1为未使用，2为正在使用

	private String lastCrawlSign;;//最新的标示数据，用作判重
    
	private int interval; //抓取频率 S

	private long delayCrawlTime; //延迟抓取
	
	private long spentCrawlTime; //抓取耗时

	private int sessionId;////调用方法标识
     
	private int top;//任务数
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public Date getLastCrawlTime() {
		return lastCrawlTime;
	}

	public void setLastCrawlTime(Date lastCrawlTime) {
		this.lastCrawlTime = lastCrawlTime;
	}
	public Date getNextCrawlTime() {
		return nextCrawlTime;
	}

	public void setNextCrawlTime(Date nextCrawlTime) {
		this.nextCrawlTime = nextCrawlTime;
	}
	public int getCrawl_level() {
		return Crawl_level;
	}

	public void setCrawl_level(int crawlLevel) {
		Crawl_level = crawlLevel;
	}

	
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
    
    public String getLastCrawlSign() {
		return lastCrawlSign;
	}

	public void setLastCrawlSign(String lastCrawlSign) {
		this.lastCrawlSign = lastCrawlSign;
	}
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	public long getDelayCrawlTime() {
		return delayCrawlTime;
	}

	public void setDelayCrawlTime(long delayCrawlTime) {
		this.delayCrawlTime = delayCrawlTime;
	}
	public long getSpentCrawlTime() {
		return spentCrawlTime;
	}

	public void setSpentCrawlTime(long spentCrawlTime) {
		this.spentCrawlTime = spentCrawlTime;
	}
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
  
	
}
