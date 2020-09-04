package demo.server.user.service;

import demo.server.user.model.User;

import java.util.List;

public interface IUserService {
    boolean addUser(User user);
    boolean delUser(User user);
    boolean editUser(User user);
    User queryUser(Integer userId);
    List<User> getAllUsers();
}
