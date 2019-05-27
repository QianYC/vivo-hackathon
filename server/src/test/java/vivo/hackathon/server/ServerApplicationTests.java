package vivo.hackathon.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vivo.hackathon.server.controller.GeneralController;
import vivo.hackathon.server.controller.UserController;
import vivo.hackathon.server.entity.User;
import vivo.hackathon.server.service.GeneralService;
import vivo.hackathon.server.service.UserService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerApplicationTests {

    @Autowired
    GeneralController controller;

    @Autowired
    UserController userController;

    @Autowired
    GeneralService generalService;

    @Autowired
    UserService userService;

    @Test
    public void test1() {
        List<String> games = controller.allGames();
        Assert.assertSame("游戏个数不为0", 0, games.size());
    }

    @Test
    public void test2() {
        List<String> games = controller.allGames();
        JSONObject jsonObject = controller.joinGame(games.get(0));
        Assert.assertSame("获取房间信息成功", "", jsonObject.toString());
    }

    @Test
    public void test3() {
        JSONObject jsonObject = controller.enterRoom("test", "0", "0");
        Assert.assertSame("能够尝试进入房间", "", jsonObject.toString());
    }

    @Test
    public void test4() {
        JSONObject jsonObject = controller.createRoom("test", "0");
        Assert.assertSame("能够尝试创建房间", "", jsonObject.toString());
    }

    @Test
    public void test5() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test", "test");
        controller.leaveRoom("0", "0", "test");
        controller.prepare("test", jsonObject);
        controller.operate("test", jsonObject);
        controller.startMatch("0", " ");
    }

    @Test
    public void test6() {
        JSONObject jsonObject = userController.register(new User("test", "test", "test"));
        Assert.assertSame("能够尝试创建用户", "", jsonObject.toString());
    }

    @Test
    public void test7() {
        Assert.assertSame("Tokenize错误", "test#1#2", generalService.tokenize("test", "1", "2"));
    }

    @Test
    public void test8() {
        Assert.assertSame("deTokenize不为100", 100, generalService.deTokenize("test#1#2"));
    }

    @Test
    public void test9() {
        JSONObject jsonObject = userService.register(new User("test", "test", "test"));
        Assert.assertSame("能够尝试注册用户", "", jsonObject.toString());
    }

}
