package vivo.hackathon.chess.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vivo.hackathon.chess.service.GameService;
import vivo.hackathon.chess.service.ServerClient;
import vivo.hackathon.chess.service.impl.RunnerServiceImpl;

@RestController
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    RestTemplate template;

    @Value("${server.service-url}")
    String server;

    @Value("${spring.application.name}")
    String gid;

    @Value("${game-pic-url}")
    String gamePicUrl;

    @Autowired
    ServerClient client;

    @GetMapping("/joinGame")
    public JSONObject  joinGame() {
        JSONObject object = new JSONObject();
        object.put("gameName", gid);
        object.put("gameIntro", gameService.getIntro());
        object.put("gamePic", gamePicUrl);
        object.put("rooms", gameService.allRooms());
        return object;
    }

    @GetMapping("/enterRoom/{rid}/{userName}")
    public JSONObject enterRoom(@PathVariable("rid") String rid, @PathVariable("userName") String userName) {
        JSONObject res = new JSONObject();
        boolean b = gameService.enterRoom(rid, userName);
        res.put("success", b);
        if (!b) {
            res.put("msg", "进入房间失败");
        }
        return res;
    }

    @GetMapping("/createRoom")
    public JSONObject createRoom() {
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("rid", gameService.createRoom());
        return res;
    }

    @GetMapping("/room/{rid}/prepare/{userName}")
    public void prepare(@PathVariable("rid") String rid, @PathVariable("userName") String userName) {
        log.info("prepare");
        if (gameService.prepare(rid, userName)) {
            new Thread(new RunnerServiceImpl(gameService.getRoom(rid), client, server, gid)).start();
        }
    }

    @PostMapping("/room/operate")
    public void operate(@RequestParam("rid") String rid,
                        @RequestParam("userName") String userName,
                        @RequestParam("data") String data) {

    }
}
