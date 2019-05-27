package vivo.hackathon.server;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vivo.hackathon.server.controller.GeneralController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerApplicationTests {

    @Autowired
    GeneralController controller;

    @Test
    public void test1() {
        JSONObject object = new JSONObject();
        object.put("op", "left");
        controller.operate("userName#game-chess#rid", object);
    }

}
