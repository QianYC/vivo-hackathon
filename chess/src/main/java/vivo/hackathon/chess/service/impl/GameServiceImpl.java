package vivo.hackathon.chess.service.impl;

import org.springframework.stereotype.Service;
import vivo.hackathon.chess.model.Room;
import vivo.hackathon.chess.service.GameService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    Map<String, Room> rooms = new HashMap<>();
    AtomicInteger nextId = new AtomicInteger(0);

    @Override
    public List<Room> allRooms() {
        return rooms.entrySet()
                .stream()
                .map(x->x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public String createRoom() {
        String id = ((Integer) nextId.getAndIncrement()).toString();
        rooms.put(id, new Room(id, 0, 2, 0, Room.STATUS_NOT_STARTED));
        return id;
    }

    @Override
    public boolean enterRoom(String rid) {
        Room room = rooms.get(rid);
        if (room == null) {
            return false;
        }
        synchronized (room) {
            int current_num = room.getCurrent_num();
            if (current_num >= room.getMax_num()) {
                return false;
            }
            room.setCurrent_num(current_num + 1);
        }
        return true;
    }

    @Override
    public void closeRoom() {
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            if (entry.getValue().getStatus() == Room.STATUS_FINISHED) {
                rooms.remove(entry.getKey());
            }
        }
    }
}
