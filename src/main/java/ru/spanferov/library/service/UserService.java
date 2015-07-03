package ru.spanferov.library.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.spanferov.library.dao.UserDAO;
import ru.spanferov.library.domain.User;

public class UserService {

    private UserDAO userDAO;
    private String[] roles = {"ROLE_MEMBER", "ROLE_MANAGER"};

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public List<User> list() {
        return userDAO.getUsers();
    }

    @Transactional
    public List<String> getUserRoles() {
        return Arrays.asList(roles);
    }

    @Transactional
    public void saveNewUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.saveUser(user);
    }
    
    @Transactional
    public void updateUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.updateUser(user);
    }

    @Transactional
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    public void deleteUserById(int id) {
        userDAO.deleteUser(userDAO.getUserById(id));
    }

}
