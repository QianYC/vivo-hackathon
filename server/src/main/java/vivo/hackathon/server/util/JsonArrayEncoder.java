package vivo.hackathon.server.util;

import com.alibaba.fastjson.JSONArray;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonArrayEncoder implements Encoder.Text<JSONArray> {
    @Override
    public String encode(JSONArray array) throws EncodeException {
        return array.toJSONString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
