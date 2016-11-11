package com.aim.game_base.net.protocal.proto;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 消息处理工厂
 *
 */
public class ProtobufMessageCodecFactory implements ProtocolCodecFactory{
	private final ProtobufMessageEncoder encoder;
	private final ProtobufMessageDecoder decoder;
	
	public ProtobufMessageCodecFactory(Charset charset) {
		this.encoder = new ProtobufMessageEncoder();
		this.decoder = new ProtobufMessageDecoder();
	}
	
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
