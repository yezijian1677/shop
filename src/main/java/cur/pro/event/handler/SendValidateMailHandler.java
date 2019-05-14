package cur.pro.event.handler;

import cur.pro.event.EventModel;
import cur.pro.event.EventType;
import cur.pro.utils.MailUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 发送注册用户时邮箱验证码事件
 */
@Service
public class SendValidateMailHandler implements EventHandler {

    public void doHandler(EventModel model) {
        MailUtil.sendValidateMail(model.getExts("mail"), model.getExts("code"));
    }

    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.SEND_VALIDATE_EMAIL);
    }
}
