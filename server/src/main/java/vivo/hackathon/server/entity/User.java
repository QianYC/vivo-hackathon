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

    private String role, username, password;

    public User(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
