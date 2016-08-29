package com.pb.server.sdk.filter;

import pb.server.dao.model.Message;
import com.pb.server.sdk.util.PBProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(MessageDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf inbuf,
			List<Object> out) throws Exception {
		logger.info("Receive from " + ctx.channel().remoteAddress() + " " + inbuf.readableBytes() + " bytes data.");
		int body_length = inbuf.readInt();
		byte encode = inbuf.readByte();
		byte enzip = inbuf.readByte();
		byte type = inbuf.readByte();
		short msg_id = inbuf.readShort();
		Message msg = new Message();
		msg.setEncode(encode);
		msg.setEnzip(enzip);
		msg.setType(type);
		msg.setMsg_id(msg_id);
		msg.setLength(body_length);
		msg.setContent(PBProtocol.Decode(encode,enzip,inbuf));
		out.add(msg);
	}

}
