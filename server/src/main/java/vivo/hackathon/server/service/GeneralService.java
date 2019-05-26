package vivo.hackathon.server.service;

import java.util.Map;

public interface GeneralService {
    String tokenize(String userName, String gid, String rid);

    Map<String, String> deTokenize(String token);
}
