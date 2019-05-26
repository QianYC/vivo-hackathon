package vivo.hackathon.server.service.impl;

import org.springframework.stereotype.Service;
import vivo.hackathon.server.service.GeneralService;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeneralServiceImpl implements GeneralService {
    @Override
    public String tokenize(String userName, String gid, String rid) {
        return String.format("%s#%s#%s", userName, gid, rid);
    }

    @Override
    public Map<String, String> deTokenize(String token) {
        Map<String, String> res = new HashMap<>();
        int p1 = token.indexOf('#');
        int p2 = token.lastIndexOf('#');
        res.put("userName", token.substring(0, p1));
        res.put("gid", token.substring(p1 + 1, p2));
        res.put("rid", token.substring(p2 + 1));
        return res;
    }
}
