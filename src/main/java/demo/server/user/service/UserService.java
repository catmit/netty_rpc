package demo.server.user.service;

import demo.server.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserService implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentHashMap<Integer, User> db = new ConcurrentHashMap<>();

    @Override
    public boolean addUser(User user) {
        db.put(user.getId(), user);
        return queryUser(user.getId()) != null;
    }

    @Override
    public boolean delUser(User user) {
        db.remove(user.getId());
        return true;
    }

    @Override
    public boolean editUser(User user) {
        db.replace(user.getId(), user);
        return true;
    }

    @Override
    public User queryUser(Integer userId) {
        User user = db.get(userId);
        if(user == null) {
            logger.info("user is nulllllllllllllllllllllllllllllllll");
            throw new RuntimeException("user is null");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Enumeration<User> elements = db.elements();
        while (elements.hasMoreElements()){
            users.add(elements.nextElement());
        }
        return users;
    }
}
