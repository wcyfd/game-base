package com.aim.game_base.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class WanServer {
	public static enum WanServerType {
		TCP, UDP;
	}

//	public static void startIOServer(IoHandlerProxy handler, InetSocketAddress inetSocketAddress) {
//		startServer(new ProtocolCodecFilter(new MessageCodecFactory(Charset.forName("UTF-8"))),
//				handler, inetSocketAddress, WanServerType.UDP);
//	}

	public static void startHttpServer(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		startServer(new HttpServerCodec(), handler, inetSocketAddress);
	}
	
	public static void startServer(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress, WanServerType type) {
		IoFilter ioFilter = new ProtocolCodecFilter(new MessageCodecFactory(Charset.forName("UTF-8")));
		switch (type) {
		case TCP:
			startServer(ioFilter, handler, inetSocketAddress);
			break;
		case UDP:
			UDPServer(ioFilter, handler, inetSocketAddress);
			break;
		}
	}
	
	private static void UDPServer(IoFilter ioFilter, IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		NioDatagramAcceptor ioAcceptor = new NioDatagramAcceptor();

		DatagramSessionConfig dsc = ioAcceptor.getSessionConfig();
		dsc.setReadBufferSize(4096);
		dsc.setSendBufferSize(1024);
		dsc.setReceiveBufferSize(1024);
		dsc.setReuseAddress(true);
		dsc.setIdleTime(IdleStatus.BOTH_IDLE, 60);

		DefaultIoFilterChainBuilder chain = ioAcceptor.getFilterChain();

		chain.addLast("codec", ioFilter);
		chain.addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));

		ioAcceptor.setHandler(handler);
		try {
			ioAcceptor.bind(inetSocketAddress);
			System.out.println("服务启动成功");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	

	public static void startServer(IoFilter ioFilter, IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		NioSocketAcceptor ioAcceptor = new NioSocketAcceptor();

		ioAcceptor.getSessionConfig().setReadBufferSize(4096);

		ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);

		ioAcceptor.getSessionConfig().setSendBufferSize(1024);
		ioAcceptor.getSessionConfig().setTcpNoDelay(false);

		DefaultIoFilterChainBuilder chain = ioAcceptor.getFilterChain();

		chain.addLast("codec", ioFilter);

		chain.addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));

		ioAcceptor.setHandler(handler);
		try {
			ioAcceptor.bind(inetSocketAddress);
			System.out.println("服务启动成功");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
