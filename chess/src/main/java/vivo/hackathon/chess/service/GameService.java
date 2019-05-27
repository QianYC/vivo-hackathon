package vivo.hackathon.chess.service;

import vivo.hackathon.chess.model.Room;

import java.util.List;

public interface GameService {
    List<Room> allRooms();

    String createRoom();

    boolean enterRoom(String rid, String userName);

    void closeRoom();

    /**
     * client准备
     * @param rid
     * @param userName
     * @return 全部client准备，游戏可以开始
     */
    boolean prepare(String rid, String userName);

    String getIntro();


}
