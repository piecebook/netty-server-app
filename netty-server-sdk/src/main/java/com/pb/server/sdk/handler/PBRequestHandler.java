package com.pb.server.sdk.handler;


import pb.server.dao.model.Message;
import com.pb.server.sdk.session.PBSession;

public interface PBRequestHandler {

	public abstract Message process(PBSession session, Message msg);

}