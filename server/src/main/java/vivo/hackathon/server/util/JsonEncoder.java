package vivo.hackathon.server.util;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonEncoder implements Encoder.Text<JSONObject> {
    @Override
    public String encode(JSONObject jsonObject) throws EncodeException {
        return jsonObject.toJSONString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
