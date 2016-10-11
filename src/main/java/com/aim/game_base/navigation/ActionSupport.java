package com.aim.game_base.navigation;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.ByteString;

public interface ActionSupport {
	public void execute(ByteString data,IoSession session);
	
}
