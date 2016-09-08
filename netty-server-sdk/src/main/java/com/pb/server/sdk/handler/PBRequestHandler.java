package com.pb.server.sdk.handler;


import pb.server.dao.model.Message;
import com.pb.server.sdk.session.PBSession;

/**
 * 请求处理器接口
 *
 * 服务端接处理客户端请求的处理器类必须实现该接口
 */
public interface PBRequestHandler {

	public abstract Message process(PBSession session, Message msg);

}