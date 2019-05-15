package cur.pro.services.impl;

import cur.pro.entity.Game;
import cur.pro.entity.Tag;
import cur.pro.entity.Tagmapper;
import cur.pro.entity.dto.GameDTO;
import cur.pro.mapper.GameMapper;
import cur.pro.mapper.ImgMapper;
import cur.pro.mapper.TagMapper;
import cur.pro.mapper.TagmapperMapper;
import cur.pro.services.TagService;
import cur.pro.utils.MsgCenter;
import cur.pro.utils.PageUtil;
import cur.pro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagmapperMapper tagmapperMapper;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private ImgMapper imgMapper;

    public String getNameById(Integer id) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            return null;
        }
        return tag.getName();
    }

    public Result<List<Tag>> getAll() {
        return Result.success(tagMapper.selectAll());
    }

    /**
     * 纯粹添加一个标签
     * @param name
     * @return
     */
    public Result<Tag> addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        if (1 == tagMapper.insert(tag)) {
            return Result.success(tag);
        } else {
            return Result.fail(MsgCenter.ERROR);
        }
    }


    /**
     * 为一个游戏添加标签
     * @param name
     * @param game
     * @return
     */
    @Transactional
    public Result addTag(String name, Integer game) {
        // 如果没有这个游戏，就返回404
        if (gameMapper.selectById(game) == null) {
            return Result.fail(MsgCenter.NOT_FOUND);
        }
        Result result = addTag(name);
        if (result.isSuccess()) {
            Tagmapper tagmapper = new Tagmapper();
            tagmapper.setGame(game);
            tagmapper.setTag(((Tag) result.getData()).getId());
            if (1 == tagmapperMapper.insert(tagmapper)) {
                return Result.success();
            }
        }
        return Result.fail(MsgCenter.ERROR);
    }

    /**
     * 新建一个标签，并且添加该标签给该游戏
     * @param tag
     * @param game
     * @return
     */
    public Result addTag(Integer tag, Integer game) {
        // 如果没有这个游戏，就返回404
        if (gameMapper.selectById(game) == null) {
            return Result.fail(MsgCenter.NOT_FOUND);
        }
        Tagmapper tagmapper = new Tagmapper();
        tagmapper.setGame(game);
        tagmapper.setTag(tag);
        if (1 == tagmapperMapper.insert(tagmapper)) {
            return Result.success();
        } else {
            return Result.fail(MsgCenter.ERROR);
        }
    }

    public Result<List<GameDTO>> getGamesByTag(Integer tag, Integer page) {
        // 没有这个标签，返回404
        if (tagMapper.selectById(tag) == null) {
            return Result.fail(MsgCenter.NOT_FOUND);
        }
        List<Integer> gameIds = tagmapperMapper.selectByTag(tag);                  // 根据标签获取所有的游戏ID
        List<Game> games = gameMapper.selectByIdsAndStat(gameIds, Game.STAT_OK);
        PageUtil pageUtil = new PageUtil(games.size(), page);
        // 假分页
        int size = pageUtil.getStartPos() + 10 > games.size() - 1 ? games.size() : pageUtil.getStartPos() + 10;
        List<GameDTO> gameDTOS = paresGameDTO(games.subList(pageUtil.getStartPos(), size));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("game", gameDTOS);
        map.put("page", pageUtil);
        return Result.success(map);
    }

    private List<GameDTO> paresGameDTO(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<GameDTO>();
        for (Game game : games) {
            List<Tag> tags = null;
            List<Integer> tagIds = tagmapperMapper.selectByGame(game.getId());     // 获取游戏的标签id
            if (tagIds.size() != 0) {
                tags = tagMapper.selectByIds(tagIds);                         // 根据id获取所有的标签信息
            }
            List<String> img = imgMapper.selectByGame(game.getId());                // 获取所有的图片
            GameDTO dto = new GameDTO(game, tags, img);
            gameDTOS.add(dto);
        }
        return gameDTOS;
    }
}
