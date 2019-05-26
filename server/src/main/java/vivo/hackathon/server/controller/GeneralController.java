package vivo.hackathon.server.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import vivo.hackathon.server.service.GeneralService;

import java.util.List;
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
    public String joinGame(@PathVariable("gid") String gid) {
        String url = String.format("http://%s/joinGame", gid);
        return template.getForObject(url, String.class);
    }

    /**
     * 加入游戏房间
     * @param gid
     * @param rid
     * @return
     */
    @GetMapping("/game/{gid}/{rid}")
    public JSONObject enterRoom(@SessionAttribute("userName") String userName, @PathVariable("gid") String gid, @PathVariable("rid") String rid) {
        JSONObject res = new JSONObject();
        String url = String.format("http://%s/enterRoom/%s", gid, rid);
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
}
