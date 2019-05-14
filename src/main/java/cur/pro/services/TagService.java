package cur.pro.services;

import cur.pro.entity.Tag;
import cur.pro.entity.dto.GameDTO;
import cur.pro.utils.Result;

import java.util.List;

public interface TagService {

    /**
     * 根据标签id获取标签名字，如果不存在返回null
     * @param id
     * @return
     */
    String getNameById(Integer id);

    /**
     * 获取所有的标签
     * @return
     */
    Result<List<Tag>> getAll();

    /**
     * 添加标签，不绑定到游戏
     * @param name
     * @return
     */
    Result<Tag> addTag(String name);

    /**
     * 添加标签并绑定到游戏
     * @param name
     * @param game
     * @return
     */
    Result addTag(String name, Integer game);

    /**
     * 绑定标签到游戏
     * @param tag
     * @param game
     * @return
     */
    Result addTag(Integer tag, Integer game);

    /**
     * 根据标签获取所有游戏
     * @param tag
     * @param page
     * @return
     */
    Result<List<GameDTO>> getGamesByTag(Integer tag, Integer page);

}
