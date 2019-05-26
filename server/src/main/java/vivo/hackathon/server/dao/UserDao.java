package vivo.hackathon.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vivo.hackathon.server.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
