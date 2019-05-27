package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vivo.hackathon.server.entity.User;
import vivo.hackathon.server.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/register")
    public JSONObject register(@RequestBody User user) {
        log.info(user.getUsername());
        log.info(user.getPassword());
        return service.register(user);
    }

}
