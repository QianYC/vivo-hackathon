package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vivo.hackathon.server.service.GeneralService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GeneralController {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate template;
    @Autowired
    GeneralService generalService;

    /**
     * 查看所有游戏
     * @return
     */
    @GetMapping("/games")
    public List<String> allGames() {
        return discoveryClient.getServices()
                .stream()
                .filter(x -> x.startsWith("game-"))
                .collect(Collectors.toList());
    }

    /**
     * 加入gid游戏
     * @param gid
     * @return
     */
    @GetMapping("/game/{gid}")
    public JSONObject joinGame(@PathVariable("gid") String gid) {
        String url = String.format("http://%s/joinGame", gid);
        return template.getForObject(url, JSONObject.class);
    }

    /**
     * 加入游戏房间
     * @param gid
     * @param rid
     * @return
     */
    @PostMapping("/game/{gid}/{rid}")
    public JSONObject enterRoom(@SessionAttribute("userName") String userName,
                                @PathVariable("gid") String gid,
                                @PathVariable("rid") String rid) {
        JSONObject res = new JSONObject();
        String url = String.format("http://%s/enterRoom/%s/%s", gid, rid, userName);
        JSONObject enterRes = template.getForObject(url, JSONObject.class);

        boolean b = enterRes.getBoolean("success");
        res.put("success", b);
        if (b) {
            res.put("token", generalService.tokenize(userName, gid, rid));
        } else {
            res.put("msg", enterRes.getString("msg"));
        }
        return res;
    }

    /**
     * 退出游戏房间
     * @param gid
     * @param rid
     * @param userName
     */
    @DeleteMapping("/game/{gid}/{rid}")
    public void leaveRoom(@PathVariable("gid") String gid,
                          @PathVariable("rid") String rid,
                          @SessionAttribute("userName") String userName) {

    }

    /**
     * 创建房间
     * @param userName
     * @param gid
     * @return
     */
    @GetMapping("/game/{gid}/create")
    public JSONObject createRoom(@SessionAttribute("userName") String userName, @PathVariable("gid") String gid) {
        JSONObject res = new JSONObject();
        String url = String.format("http://%s/createRoom", gid);
        JSONObject createRes = template.getForObject(url, JSONObject.class);

        boolean b = createRes.getBoolean("success");
        res.put("success", b);
        if (b) {
            res.put("token", generalService.tokenize(userName, gid, createRes.getString("rid")));
        } else {
            res.put("msg", createRes.getString("msg"));
        }
        return res;
    }

    /**
     * client投票开始比赛
     * @param token
     * @param data
     */
    public void prepare(String token, JSONObject data) {
        Map<String, String> map = generalService.deTokenize(token);
        String userName = map.get("userName");
        String gid = map.get("gid");
        String rid = map.get("rid");

        String url = String.format("http://%s/room/%s/prepare/%s", gid, rid, userName);
        template.getForObject(url, Void.class);
    }

    /**
     * client进行操作
     * @param token
     * @param data
     */
    public void operate(String token, JSONObject data) {
        Map<String, String> map = generalService.deTokenize(token);
        String userName = map.get("userName");
        String gid = map.get("gid");
        String rid = map.get("rid");

        String url = String.format("http://%s/room/operate", gid);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("rid", rid);
        params.add("userName", userName);
        params.add("data", data);
        template.postForObject(url, params, Void.class);
    }

    /**
     * 通知前端比赛开始
     * @param gid
     * @param rid
     */
    @GetMapping("/start/{gid}/{rid}")
    public void startMatch(@PathVariable("gid") String gid,
                           @PathVariable("rid") String rid) {

    }
}
