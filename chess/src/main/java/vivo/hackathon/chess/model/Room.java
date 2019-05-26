package vivo.hackathon.chess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    public final static int STATUS_NOT_STARTED = 0, STATUS_RUNNING = 1, STATUS_FINISHED = 2;

    private String rid;
    //当前人数，最大人数，投票开始人数，游戏状态
    private int current_num, max_num, vote_num, status;

}
