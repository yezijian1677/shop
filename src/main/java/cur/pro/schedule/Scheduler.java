package cur.pro.schedule;

import cur.pro.services.OrderService;
import cur.pro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务，自动完成事件
 */
@Component
public class Scheduler {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedRate = 1000 * 60 * 3)
    public void delNotValidateUser() {
        userService.delNotValidateUser();
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void expireToken() {
        userService.expireToken();
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void cancelOrder() {
        orderService.autoCancelOrder();
    }

}
