package vivo.hackathon.chess.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vivo.hackathon.chess.model.Payload;
import vivo.hackathon.chess.model.Player;
import vivo.hackathon.chess.model.Room;
import vivo.hackathon.chess.service.RunnerService;
import vivo.hackathon.chess.service.ServerClient;

import java.util.List;

@Slf4j
public class RunnerServiceImpl implements RunnerService {
//    private char[][] map = new char[15][15];
    private Room room;
    private List<Player> players;
//    private RestTemplate template;
    private ServerClient client;
    private String server, gid;

    public RunnerServiceImpl(Room room, ServerClient client, String server, String gid) {
        this.room = room;
        players = room.getPlayers();
        this.client = client;
        this.server = server;
        this.gid = gid;
    }

    @Override
    public void run() {
        log.info("runner start");
        JSONArray positions0 = new JSONArray();
        JSONArray positions1 = new JSONArray();
        try {
            for(int i=0;i<9;i++){
                JSONArray steps = new JSONArray();
                JSONObject step = new JSONObject();
                JSONObject position = new JSONObject();
                if(i%2==0) {
                    position.put("x", 6);
                    position.put("y", i / 2 + 5);
                    positions0.add(position);
                } else {
                    position = new JSONObject();
                    position.put("x", 7);
                    position.put("y", i / 2 + 5);
                    positions1.add(position);
                }
                step.put("username", players.get(0).getUserName());
                step.put("positions", positions0);
                steps.add(step);
                step = new JSONObject();
                step.put("username", players.get(1).getUserName());
                step.put("positions", positions1);
                steps.add(step);
                System.out.println(steps);

                client.sendSteps(new Payload(gid, room.getRoomId(), steps));

//                String url = String.format("http://%s/operate", server);
//                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//                params.add("gid", gid);
//                params.add("rid", room.getRoomId());
//                params.add("map", steps);
//                template.postForObject(url, params, Void.class);
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("runner finish");
    }
}
