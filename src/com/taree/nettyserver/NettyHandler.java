package com.taree.nettyserver;

import java.util.List;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;


/**
 * server接收消息用的handler 
 * 
 * @author Ransom
 * 
 */
public class NettyHandler extends SimpleChannelUpstreamHandler {
//    @Autowired
//    private KeywordService keywordService;

	/**
	 * 服务端收到客户端请求
	 */
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {

        System.out.println(e.getMessage());
		
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		
//		SimpleMailSender.getInstance().send("客户端断开连接", e.getChannel().getRemoteAddress().toString());
		System.out.println("Client has a error,Error cause:" + e.getCause());
		e.getChannel().close();
		
	}
}

//System.out.println("-----"+e.getMessage());
//KeywordBean2 keywordBean = (KeywordBean2) e.getMessage();
//
//switch(keywordBean.getSessionId()){
//case Code.CODE_GETJOB:
//    List<KeywordBean> keywordBeanList=keywordService.keywordBeanList(keywordBean.getTop());		
//	System.out.println("获取资源"+keywordBeanList);
//	ctx.getChannel().write(keywordBeanList);
////	log.info(e.getRemoteAddress().toString() + "获取任务");
//	
//	break;
//case Code.CODE_REALEASE:
//	System.out.println("释放资源");
//	
//	break;
//	
//  
//}
//switch (site.getSessionId()) {
//case Code.CODE_GETJOB: //获取任务
//	List<Site> list = ProbeEntryQueue.getInstance().getJobs(
//			e.getRemoteAddress().toString(), site.getTop());
//	ctx.getChannel().write(list);
//	log.info(e.getRemoteAddress().toString() + "获取任务");
//	break;
//case Code.CODE_REALEASE: //释放任务
//	ProbeEntryQueue.getInstance().realease(site);
//	log.info(site.getName() + "释放任务");
//	break;
//
//}