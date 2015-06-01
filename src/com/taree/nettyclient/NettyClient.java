package com.taree.nettyclient;

import static org.jboss.netty.channel.Channels.pipeline;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;



/**
 * 启动一个client线程，用来间歇性的发送消息
 * 
 * @author Ransom
 */
public class NettyClient {
	private Channel future;
	private static NettyClient instance = null;

	public static synchronized NettyClient getInstance() {
		if (null == instance) {
			instance = new NettyClient();
		}

		return instance;
	}

	public Channel getChannel() {
		return future;
	}

	public void setChannel(Channel future) {
		this.future = future;
	}

	public boolean startService() {
		/*
		 * 实例化一个客户端Bootstrap实例， NioClientSocketChannelFactory是Netty默认提供的。
		 * 两个参数，一个是boss的线程池，一个是worker执行的线程池。
		 * 两个线程池都使用了java.util.concurrent.Executors中的线程池来创建。
		 */
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		/*
		 * 设置piplineFactory, 顾名思义，就是产生默认的pipline。
		 * pipline的实例是DefaultChannelPipeline 提供了链式的事件通讯机制
		 */
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
			 */
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				/*
				 * 在DefaultChannelPipeline的过滤器 链中实现了 encode 、decode、handler
				 * 其中encode实现自ChannelDownstreamHandler接口
				 * decode、Handler实现自ChannelUpstreamHandler接口
				 * 也就说明了在client发送消息的时候，默认按照顺序会先调用decode
				 * 在client接收到响应的时候，会按照顺序调用encode和Handler。
				 * 后面会有文章专门将ChannelDownstreamHandler和ChannelUpstreamHandler的调用顺序
				 * 。
				 */
				ChannelPipeline pipleline = pipeline();
				pipleline.addLast("encode", new ObjectEncoder());
				pipleline.addLast(
						"decoder",
						new ObjectDecoder(ClassResolvers.cacheDisabled(this
								.getClass().getClassLoader())));
				pipleline.addLast("handler", new NettyHandler());
				return pipleline;
			}
		});

		/*
		 * 与127.0.0.1建立长连接。
		 */
		future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6090))
				.getChannel();
		//future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6090))
		//		.getChannel();
		
		return future.isOpen();
	}

	// /**
	// * 发送消息至server
	// */
	// public void sendMsg()
	// {
	// if(future==null) return;
	// Site p = new Site();
	// p.setName("ransom");
	// p.setSessionId(1);
	// p.setTop(1);
	// future.getChannel().write(p);
	//
	// }

}
