package com.taree.nettyclient;

import java.util.ArrayList;
import java.util.List;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.baifendian.bean.KeywordBean;
import com.baifendian.bean.Site;
import com.baifendian.utils.mail.SimpleMailSender;
import com.taree.nettyclient.NettyClient;

/**
 * client和server接收消息共用的handler 由于两个都是继承自SimpleChannelUpstreamHandler,所以就写在一起了。
 * 
 * @author Ransom
 * 
 */
public class NettyHandler extends SimpleChannelUpstreamHandler {

	// public static CountableThreadPool executor = new CountableThreadPool(1);

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		System.out.println(e.getMessage());
		ArrayList<KeywordBean> list=(ArrayList)e.getMessage();
		TestGetRelease.crawl(list);
//		@SuppressWarnings("unchecked")
//		List<Site> jobs = (List<Site>) e.getMessage();
//		System.out.println("获取到" + jobs.size() + "个任务");

//		InternetCrawlDaemon.getInstance().addJobs(jobs); // 获取到的任务添加进去
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
//		if (e.getChannel() != null&&e.getChannel().getRemoteAddress()!=null) {
//			SimpleMailSender.getInstance().send("服务端异常", e.getChannel().getRemoteAddress().toString());
//		}
		boolean flag = false;
		while (!NettyClient.getInstance().getChannel().isOpen()) {

			System.out.println("重试连接:" + "Server has a error,Error cause:"
					+ e.getCause());
			flag = NettyClient.getInstance().startService();
			
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (flag == true) {
				break;
			}

			e.getChannel().close();
		}

	}
}
