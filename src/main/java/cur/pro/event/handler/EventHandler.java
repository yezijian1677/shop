package cur.pro.event.handler;

import cur.pro.event.EventModel;
import cur.pro.event.EventType;

import java.util.List;

/**
 * 事件处理接口，包含了事件处理以及关心的所有事件
 */
public interface EventHandler {

    /**
     * 处理事件
     */
    void doHandler(EventModel eventModel);

    /**
     * 获取支持的事件类型
     * @return
     */
    List<EventType> getSupportEvent();

}
