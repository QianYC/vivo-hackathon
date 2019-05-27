package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vivo.hackathon.server.util.JsonArrayEncoder;
import vivo.hackathon.server.util.JsonDecoder;
import vivo.hackathon.server.util.JsonEncoder;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/browser/{gid}/{rid}",
        encoders = {JsonArrayEncoder.class},
        decoders = {JsonDecoder.class})
@Component
@Slf4j
public class BrowserWebSocket {
//    static Map<String, Session> idSessions = new HashMap<>();
    static MultiValueMap<String, Session> idSessions = new LinkedMultiValueMap<>();


    @OnOpen
    public void onOpen(@PathParam("gid") String gid, @PathParam("rid") String rid, Session session) {
        log.info("browser open socket");
        idSessions.add(String.format("%s#%s", gid, rid), session);
    }

    @OnClose
    public void onClose(@PathParam("gid") String gid, @PathParam("rid") String rid, Session session) {
        log.info("browser close socket");
        String key = String.format("%s#%s", gid, rid);
        List<Session> sessions = idSessions.get(key);
        sessions.remove(session);
        idSessions.addAll(key, sessions);
    }

    public void sendMessage(String gid, String rid, JSONArray array) throws IOException, EncodeException {
        List<Session> sessions = idSessions.get(String.format("%s#%s", gid, rid));
        for (Session session : sessions) {
            session.getBasicRemote().sendObject(array);
        }
    }
}
