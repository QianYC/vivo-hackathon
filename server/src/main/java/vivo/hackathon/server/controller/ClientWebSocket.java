package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vivo.hackathon.server.util.JsonDecoder;
import vivo.hackathon.server.util.JsonEncoder;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint(value = "/client/{token}",
        encoders = {JsonEncoder.class},
        decoders = {JsonDecoder.class})
@Component
@Slf4j
public class ClientWebSocket {
    static Map<String, Session> tokenSessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException, EncodeException {
        log.info("open web socket: {}", token);
        tokenSessions.put(token, session);
//        JSONObject object = new JSONObject();
//        object.put("success", true);
//        session.getBasicRemote().sendObject(object);
    }

    @OnClose
    public void onClose(Session session) {

        log.info("close web socket");
    }

    @OnMessage
    public void onMessage(JSONObject object, Session session) {
        log.info("receive msg: {}", object);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("web socket error");
        throwable.printStackTrace();
    }
}
