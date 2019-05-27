package vivo.hackathon.server.service;

import com.alibaba.fastjson.JSONObject;
import vivo.hackathon.server.entity.User;

public interface UserService {

    JSONObject register(User user);
}
