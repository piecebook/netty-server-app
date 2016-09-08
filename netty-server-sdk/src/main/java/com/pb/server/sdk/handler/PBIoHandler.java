package com.pb.server.sdk.handler;


import com.pb.server.sdk.constant.PBCONSTANT;
import pb.server.dao.model.Message;
import com.pb.server.sdk.session.PBSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 请求分配处理器
 * <p>
 * 客户端所有的请求都经过该类
 * 该类根据请求消息的type字段，判断出请求类型，并分配到对应的请求处理器
 * 该类与用户一一对应
 */
public class PBIoHandler extends SimpleChannelInboundHandler<Message> {
    private static Logger logger = LoggerFactory.getLogger(PBIoHandler.class);

    //所有的请求处理器
    private Map<String, PBRequestHandler> handlers;

    //用户uid
    private String uid = null;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg)
            throws Exception {
        logger.info("Received from " + ctx.channel().remoteAddress() + " "
                + msg.toString());
        if (msg != null) {
            //封装用户连接session
            PBSession pbSession = new PBSession(ctx.channel());
            pbSession.setUid(uid);

            //请求回复
            Message reply = null;
            switch (msg.getType()) {
                //登录请求
                case PBCONSTANT.LOGIN_FLAG:
                    reply = handlers.get(PBCONSTANT.LOGIN).process(pbSession, msg);
                    if (reply.get("st").equals(PBCONSTANT.SUCCESS)) uid = reply.get("r_uid");
                    break;
                //消息转发请求
                case PBCONSTANT.MESSAGE_FLAG:
                    reply = handlers.get(PBCONSTANT.MESSAGE).process(pbSession, msg);
                    break;
                //消息ACK答复
                case PBCONSTANT.ACK_FLAG:
                    reply = handlers.get(PBCONSTANT.ACK).process(pbSession, msg);
                    break;
                //注销请求
                case PBCONSTANT.LOGOUT_FLAG:
                    reply = handlers.get(PBCONSTANT.LOGOUT).process(pbSession, msg);
                    break;
                //ping心跳请求
                case PBCONSTANT.PING_FLAG:
                    reply = handlers.get(PBCONSTANT.PING).process(pbSession, msg);
                    break;
                default:
            }
            pbSession.write(reply);
        }
    }

    public void setHandlers(Map<String, PBRequestHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Connected from:" + ctx.channel().remoteAddress());
    }

    /**
     * 用户掉线监听
     * 注销掉线用户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("unregistered");
        PBSession pbSession = new PBSession(ctx.channel());
        pbSession.setUid(uid);
        handlers.get(PBCONSTANT.LOGOUT).process(pbSession, null);
    }


}
