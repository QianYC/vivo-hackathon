package vivo.hackathon.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String role, userName, password;

    public User(String role, String userName, String password) {
        this.role = role;
        this.userName = userName;
        this.password = password;
    }
}
