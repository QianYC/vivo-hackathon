package vivo.hackathon.server.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.userdetails.UserDetailsService;
import vivo.hackathon.server.entity.User;

public interface UserService extends UserDetailsService {
    JSONObject register(User user);
}
