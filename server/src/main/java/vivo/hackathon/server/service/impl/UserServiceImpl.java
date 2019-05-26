package vivo.hackathon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vivo.hackathon.server.config.UserDetailsImpl;
import vivo.hackathon.server.dao.UserDao;
import vivo.hackathon.server.entity.User;
import vivo.hackathon.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = dao.findByUserName(s);
        if (user==null) throw new UsernameNotFoundException("user not found");
        return new UserDetailsImpl(user);
    }
}
