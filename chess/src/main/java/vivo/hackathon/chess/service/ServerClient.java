package vivo.hackathon.chess.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vivo.hackathon.chess.model.Payload;

@FeignClient("${server.service-url}")
public interface ServerClient {
    @PostMapping("/operate")
    void sendSteps(@RequestBody Payload payload);

}
