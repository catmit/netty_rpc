package demo.client.user;

import demo.server.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.client.proxy.Proxy;

public class UserClient {
    private final static Logger logger = LoggerFactory.getLogger(UserClient.class);

    public static void main(String[] args) {
        User user = new User(1, "hello");
        UserService userService = (UserService) Proxy.createBean(UserService.class);
        if(userService.addUser(user)){
            logger.info("添加成功 开始查询");
            user = userService.queryUser(user.getId());
            logger.info(user.toString());
        }else logger.info("添加失败");
    }
}
