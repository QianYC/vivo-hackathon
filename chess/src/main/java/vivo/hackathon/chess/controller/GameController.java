package vivo.hackathon.chess.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vivo.hackathon.chess.model.Room;
import vivo.hackathon.chess.service.GameService;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/joinGame")
    public List<Room> joinGame() {
        return gameService.allRooms();
    }

    @GetMapping("/enterRoom/{rid}")
    public JSONObject enterRoom(@PathVariable("rid") String rid) {
        JSONObject res = new JSONObject();
        boolean b = gameService.enterRoom(rid);
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
}
