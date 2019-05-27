package vivo.hackathon.chess.model;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Room {
    public final static int STATUS_NOT_STARTED = 0, STATUS_RUNNING = 1, STATUS_FINISHED = 2;

    private String roomId;
    //当前人数，最大人数，投票开始人数，游戏状态
    private int currentCount, maxCount, voteCount, status;

    private List<Player> players = new ArrayList<>();

    public void addPlayer(String player) {
        players.add(new Player(player, Player.STATUS_UNPREPARED));
    }

    public void delPlayer(String player) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUserName().equals(player)) {
                iterator.remove();
                break;
            }
        }
    }

    public Room(String roomId, int currentCount, int maxCount, int voteCount, int status) {
        this.roomId = roomId;
        this.currentCount = currentCount;
        this.maxCount = maxCount;
        this.voteCount = voteCount;
        this.status = status;
    }
}
