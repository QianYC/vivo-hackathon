package vivo.hackathon.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vivo.hackathon.server.dao.UserDao;
import vivo.hackathon.server.entity.Role;
import vivo.hackathon.server.entity.User;
import vivo.hackathon.server.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao dao;

    @Override
    public JSONObject register(User user) {
        JSONObject object = new JSONObject();
        log.info("username: {}", user.getUsername());
        User user1 = dao.findByUsername(user.getUsername());
        user.setRole(Role.ROLE_USER);
        object.put("success", user1 == null);
        if (user1 == null) {
            dao.saveAndFlush(user);
        }
        return object;
    }
}
