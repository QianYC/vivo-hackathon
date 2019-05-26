package vivo.hackathon.server.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/home")
    public String home() {
        return "user home";
    }
}
