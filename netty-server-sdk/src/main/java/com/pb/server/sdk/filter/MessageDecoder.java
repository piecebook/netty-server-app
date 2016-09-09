package com.pb.server.sdk.filter;

import pb.server.dao.model.Message;
import com.pb.server.sdk.util.PBProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 消息解码器
 *
 * 网络传输的消息都在这里解码
 */
public class MessageDecoder extends ByteToMessageDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(MessageDecoder.class);

	/**
	 *
	 * @param ctx 连接context,里面含有session
	 * @param inbuf 输入缓冲区
	 * @param out 传到下一个handler的list
	 * @throws Exception
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf inbuf,
			List<Object> out) throws Exception {
		logger.info("Receive from " + ctx.channel().remoteAddress() + " " + inbuf.readableBytes() + " bytes data.");
		//消息头解码
		int body_length = inbuf.readInt();
		byte encode = inbuf.readByte();
		byte enzip = inbuf.readByte();
		byte type = inbuf.readByte();
		short msg_id = inbuf.readShort();//msg_id长度两个字节
		Message msg = new Message();
		msg.setEncode(encode);
		msg.setEnzip(enzip);
		msg.setType(type);
		msg.setMsg_id(msg_id);
		msg.setLength(body_length);
		//PBProtocol 解码消息体
		msg.setContent(PBProtocol.Decode(encode,enzip,inbuf));
		//设置时间
		msg.setTime(System.currentTimeMillis());
		out.add(msg);
	}

}
