package cur.pro.event.handler;

import cur.pro.event.EventModel;
import cur.pro.event.EventType;
import cur.pro.utils.MailUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 发送找回密码邮件事件
 */
@Service
public class SendFindPwdMailHandler implements EventHandler {

    public void doHandler(EventModel model) {
        MailUtil.sendFetchPwdMail(model.getExts("mail"), model.getExts("code"));
    }

    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.SEND_FIND_PWD_EMAIL);
    }
}
