package ru.spanferov.library.dao;

import java.util.List;
import ru.spanferov.library.domain.User;

public interface UserDAO {

    public User getUserByEmail(String email);

    public List<User> getUsers();

    public void saveUser(User user);
    
    public void updateUser(User user);

    public User getUserById(int id);

    public void deleteUser(User user);

}
