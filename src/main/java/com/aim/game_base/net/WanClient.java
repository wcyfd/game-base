package com.aim.game_base.net;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.aim.game_base.entity.net.base.Protocal.CS;
import com.aim.game_base.entity.net.base.Protocal.SC;
import com.aim.game_base.net.ClientMessageCodecFactory;
import com.aim.game_base.net.IoHandlerAdapter;

public class WanClient {
	public enum WanClientType {
		TCP;
	}

	private IoSession session = null;
	private IoConnector connector = null;
	private InetSocketAddress inetSocketAddress = null;

	public void startClient(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress, WanClientType type) {
		this.startClient(new ProtocolCodecFilter(new ClientMessageCodecFactory(Charset.forName("UTF-8"))), handler,
				inetSocketAddress, type);
	}

	public void startClient(IoFilter ioFilter, IoHandlerAdapter handler, InetSocketAddress inetSocketAddress,
			WanClientType type) {
		switch (type) {
		case TCP:
			tcp(ioFilter, handler, inetSocketAddress);
			break;
		}

	}

	public void tcp(IoFilter ioFilter, IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
		connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec", ioFilter);
		// connector.getFilterChain().addLast("threadpool", new
		// ExecutorFilter(Executors.newCachedThreadPool()));
		connector.setHandler(handler);
		this.inetSocketAddress = inetSocketAddress;
	}

	private IoSession getSession() {
		if (session == null || !session.isConnected()) {
			try {
				ConnectFuture future = connector.connect(inetSocketAddress);
				future.awaitUninterruptibly();
				session = future.getSession();
			} catch (RuntimeIoException e) {
				e.printStackTrace();
			}
		}
		return session;
	}

	public void send(CS cs) {
		getSession().write(cs);
	}

}
