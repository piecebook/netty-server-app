package com.pb.server.sdk.filter;

import pb.server.dao.model.Message;
import com.pb.server.sdk.util.PBProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf outbuf)
            throws Exception {
        msg.getContent().put("tm", msg.getTime().toString());
        byte[] body = PBProtocol.Encode(msg.getEncode(), msg.getEnzip(), msg.getContent());
        int body_length = body.length;
        outbuf.writeInt(body_length);
        outbuf.writeByte(msg.getEncode());
        outbuf.writeByte(msg.getEnzip());
        outbuf.writeByte(msg.getType());
        byte low = (byte) msg.getMsg_id();
        byte high = (byte) (msg.getMsg_id() >> 8);
        outbuf.writeByte(high);
        outbuf.writeByte(low);
        outbuf.writeBytes(body);
        //System.out.println(outbuf.readableBytes());
        ctx.flush();
    }

}
