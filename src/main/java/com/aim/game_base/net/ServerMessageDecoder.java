package com.aim.game_base.net;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.aim.game_base.entity.net.base.Protocal.CS;

public class ServerMessageDecoder extends CumulativeProtocolDecoder {
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

		in.order(ByteOrder.LITTLE_ENDIAN);

		int remaining = in.remaining();
		if (remaining >= 4) {
			in.mark();

			int size = in.getInt();
			if (size > 2048) {
//				 throw new NumberFakeException("msg size too long");
			}

			if (size > in.remaining()) {
				in.reset();
				return false;
			}

			byte[] data = new byte[size];

			in.get(data);

			IoBuffer params = IoBuffer.wrap(data);
			params.order(ByteOrder.LITTLE_ENDIAN);
			
			CS request = CS.parseFrom(data);
			out.write(request);

			if (in.remaining() > 0) {
				return true;
			}

		}

		return false;
	}
}
