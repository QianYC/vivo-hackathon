package vivo.hackathon.chess.service;

import vivo.hackathon.chess.model.Room;

import java.util.List;

public interface GameService {
    List<Room> allRooms();

    String createRoom();

    boolean enterRoom(String rid);

    void closeRoom();
}
