package vivo.hackathon.chess.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vivo.hackathon.chess.model.Player;
import vivo.hackathon.chess.model.Room;
import vivo.hackathon.chess.service.GameService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    Map<String, Room> rooms;

    public GameServiceImpl() {
        rooms = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            String id = Integer.toString(i);
            rooms.put(id, new Room(id, 0, 2, 0, Room.STATUS_NOT_STARTED));
        }
    }

    @Value("${api-introduction}")
    String intro;

    @Override
    public List<Room> allRooms() {
        return rooms.entrySet()
                .stream()
                .map(x->x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public String createRoom() {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public boolean enterRoom(String rid, String userName) {
        Room room = rooms.get(rid);
        if (room == null) {
            return false;
        }
        synchronized (room) {
            int current_num = room.getCurrentCount();
            if (current_num >= room.getMaxCount()) {
                return false;
            }
            room.addPlayer(userName);
            room.setCurrentCount(current_num + 1);
        }
        return true;
    }

    @Override
    public void closeRoom() {
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            if (entry.getValue().getStatus() == Room.STATUS_FINISHED) {
                rooms.remove(entry);
            }
        }
    }

    @Override
    public boolean prepare(String rid, String userName) {
        Room room = rooms.get(rid);
        synchronized (room) {
            room.setVoteCount(room.getVoteCount() + 1);
            Iterator<Player> iterator = room.getPlayers().iterator();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                if (player.getUserName().equals(userName)) {
                    player.setStatus(Player.STATUS_PREPARED);
                    break;
                }
            }
            return room.getCurrentCount() == room.getVoteCount();
        }
    }

    @Override
    public String getIntro() {
        return intro;
    }

    @Override
    public Room getRoom(String rid) {
        return rooms.get(rid);
    }
}
