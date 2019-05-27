package vivo.hackathon.server.service;

import java.util.HashMap;
import java.util.Map;

public interface GeneralService {
    String tokenize(String userName, String gid, String rid);

    static Map<String, String> deTokenize(String token){
        Map<String, String> res = new HashMap<>();
        int p1 = token.indexOf('_');
        int p2 = token.lastIndexOf('_');
        res.put("userName", token.substring(0, p1));
        res.put("gid", token.substring(p1 + 1, p2));
        res.put("rid", token.substring(p2 + 1));
        return res;
    }
}
