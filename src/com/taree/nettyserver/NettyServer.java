package com.taree.nettyserver;

import static org.jboss.netty.channel.Channels.pipeline;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 启动netty服务
 * 
 * @author qjp
 * 
 */
public class NettyServer {
	public static final Logger log = LoggerFactory
			.getLogger(NettyServer.class);
	
	private static NettyServer instance = null;

	public static synchronized NettyServer getInstance() {
		if (null == instance) {
			instance = new NettyServer();
		}

		return instance;
	}

	/**
	 * 开启netty通信服务
	 */
	public  void startNettyService() {
		/*
		 * server的注释和client类似，在这里就不重复了 但是需要注意的是server初始化的是ServerBootstrap的实例
		 * client初始化的是ClientBootstrap，两个是不一样的。
		 * 里面的channelfactory也是NioServerSocketChannelFactory。
		 */

		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
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

		bootstrap.bind(new InetSocketAddress(6090));
		log.info("server start at "+6090);
		System.out.println("---------->start successfully ---");

	}
}
