package vivo.hackathon.server.util;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class JsonDecoder implements Decoder.Text<JSONObject> {
    @Override
    public JSONObject decode(String s) throws DecodeException {
        return JSONObject.parseObject(s);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
