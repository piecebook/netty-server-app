package com.pb.server.sdk.filter;

import pb.server.dao.model.Message;
import com.pb.server.sdk.util.PBProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 * <p>
 * 网络传输的消息都会在这里进行编码
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    /**
     * @param ctx    连接context，,里面含有session
     * @param msg    发送的消息
     * @param outbuf 输出缓存区
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf outbuf)
            throws Exception {
        //把时间放进消息体传输
        //msg.getContent().put("tm", msg.getTime().toString());
        //PBProtocol协议 编码消息：消息体
        byte[] body = PBProtocol.Encode(msg.getEncode(), msg.getEnzip(), msg.getContent());
        int body_length = body.length;

        //消息头
        outbuf.writeInt(body_length);
        outbuf.writeByte(msg.getEncode());
        outbuf.writeByte(msg.getEnzip());
        outbuf.writeByte(msg.getType());
        //消息id 分成两个字节编码
        //byte low = (byte) msg.getMsg_id();//id低8位
        //byte high = (byte) (msg.getMsg_id() >> 8);//id高8位
        long id = msg.getMsg_id();
        byte[] msg_id = new byte[8];
        for (int i = 0; i < 8; i++) {
            msg_id[i] = (byte) id;
            id = id >> 8;
        }
        outbuf.writeBytes(msg_id);
        //outbuf.writeByte(high);
        //outbuf.writeByte(low);
        outbuf.writeBytes(body);

        //这里的代码可以重构一下，将 消息头，消息体 都用PBProtocol处理

        //System.out.println(outbuf.readableBytes());
        ctx.flush();
    }

}
