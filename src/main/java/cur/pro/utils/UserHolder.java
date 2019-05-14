package cur.pro.utils;

import cur.pro.entity.User;
import org.springframework.stereotype.Component;

/**
 * 每次请求时存放请求者的信息
 */
@Component
public class UserHolder {

    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void remove() {
        users.remove();
    }

}
