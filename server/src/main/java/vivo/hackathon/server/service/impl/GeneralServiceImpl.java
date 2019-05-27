package vivo.hackathon.server.service.impl;

import org.springframework.stereotype.Service;
import vivo.hackathon.server.service.GeneralService;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeneralServiceImpl implements GeneralService {
    @Override
    public String tokenize(String userName, String gid, String rid) {
        return String.format("%s_%s_%s", userName, gid, rid);
    }
}
