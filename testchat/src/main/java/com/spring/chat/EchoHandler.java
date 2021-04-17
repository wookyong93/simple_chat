package com.spring.chat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequestMapping("/echo")
public class EchoHandler extends TextWebSocketHandler{
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		sessionList.add(session);
		logger.info("{}연결됨",session.getId());
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		logger.info("{}로 부터 {}받음",session.getId(), message.getPayload());
		//모든 유저에게 메세지 출력
		for(WebSocketSession sess : sessionList) {
			sess.sendMessage(new TextMessage(message.getPayload()));
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		sessionList.remove(session);
		logger.info("{}연결 끊김",session.getId());
	}
	
	
}
