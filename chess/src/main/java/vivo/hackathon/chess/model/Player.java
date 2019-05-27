package vivo.hackathon.chess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    public final static String STATUS_PREPARED = "prepared";
    public final static String STATUS_UNPREPARED = "unprepared";

    private String userName;
    private String status;
}
