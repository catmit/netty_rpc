package demo.client.user;

import rpc.annotion.svc;
import demo.server.user.model.User;
import demo.server.user.service.IUserService;

import java.util.List;

public class UserService implements IUserService {
    @Override
    @svc(svcName = "demo.server.user.service.UserService@addUser")
    public boolean addUser(User user) {
        return false;
    }

    @Override
    @svc(svcName = "demo.server.user.service.UserService@delUser")
    public boolean delUser(User user) {
        return false;
    }

    @Override
    @svc(svcName = "demo.server.user.service.UserService@editUser")
    public boolean editUser(User user) {
        return false;
    }

    @Override
    @svc(svcName = "demo.server.user.service.UserService@queryUser")
    public User queryUser(Integer userId) {
        return null;
    }

    @Override
    @svc(svcName = "demo.server.user.service.UserService@getAllUsers")
    public List<User> getAllUsers() {
        return null;
    }
}
