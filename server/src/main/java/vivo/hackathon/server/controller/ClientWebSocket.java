package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoader;
import vivo.hackathon.server.service.GeneralService;
import vivo.hackathon.server.util.DataFormat;
import vivo.hackathon.server.util.JsonDecoder;
import vivo.hackathon.server.util.JsonEncoder;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/client/{token}",
        encoders = {JsonEncoder.class},
        decoders = {JsonDecoder.class})
@Component
@Slf4j
public class ClientWebSocket {
    static Map<String, Session> tokenSessions = new HashMap<>();

    @Autowired
    GeneralController controller;

    private static GeneralController generalController;

    @PostConstruct
    public void post() {
        generalController = controller;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        log.info("open web socket: {}", token);
        tokenSessions.put(token, session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.info("close web socket");
        for (Map.Entry<String, Session> entry : tokenSessions.entrySet()) {
            if (entry.getValue() == session) {
                tokenSessions.remove(entry);
            }
        }
    }

    @OnMessage
    public void onMessage(@PathParam("token") String token, JSONObject object, Session session) {
        log.info("token: {}", token);
        log.info("receive msg: {}", object);

        JSONObject data = JSONObject.parseObject(object.getString(DataFormat.DATA));
        switch (object.getString(DataFormat.OP)) {
            case DataFormat.OP_OPERATION:
//                generalController.operate(token, data);
                break;
            case DataFormat.OP_PREPARE:
                generalController.prepare(token, data);
                break;
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("web socket error");
        throwable.printStackTrace();
    }
}
