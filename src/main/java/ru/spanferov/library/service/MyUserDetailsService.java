package ru.spanferov.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import ru.spanferov.library.dao.UserDAO;

public class MyUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        ru.spanferov.library.domain.User user = userDAO.getUserByEmail(username);
        // CREATE USER ROLE LIST
        //List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return buildUserForAuthentication(user, authorities);

    }

    // Converts ru.spanferov.library.domain.User to org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(ru.spanferov.library.domain.User user, List<GrantedAuthority> authorities) {
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
