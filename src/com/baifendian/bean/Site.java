package com.baifendian.bean;

public class Site extends BaseNetty{
	
	private static final long serialVersionUID = -8443519314107050709L;
	private int id;
	private String lastCrawlSign; //上次抓取微博标识
	private int interval; //抓取频率 S
	private String keywords; //关键词
	private int status;  //状态 
	private long lastCrawlTime; //上次抓取时间
	private long nextCrawlTime; //下次抓取时间
	private long delayCrawlTime; //延迟抓取
	private long spentCrawlTime; //抓取耗时
	private int userid; //所属用户id
	private int media; //媒体类型
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getLastCrawlTime() {
		return lastCrawlTime;
	}
	public void setLastCrawlTime(long lastCrawlTime) {
		this.lastCrawlTime = lastCrawlTime;
	}
	public long getNextCrawlTime() {
		return nextCrawlTime;
	}
	public void setNextCrawlTime(long nextCrawlTime) {
		this.nextCrawlTime = nextCrawlTime;
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
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMedia() {
		return media;
	}
	public void setMedia(int media) {
		this.media = media;
	}
	
	
	

}
