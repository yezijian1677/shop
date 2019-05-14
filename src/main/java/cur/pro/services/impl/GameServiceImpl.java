package cur.pro.services.impl;

import cur.pro.entity.Game;
import cur.pro.entity.Tag;
import cur.pro.entity.dto.GameDTO;
import cur.pro.mapper.*;
import cur.pro.services.GameService;
import cur.pro.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private KindMapper kindMapper;
    @Autowired
    private KindmapperMapper kindmapperMapper;
    @Autowired
    private TagmapperMapper tagmapperMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private RedisUtil<GameDTO> redisUtil;
    @Autowired
    private RedisPoolUtil redisPoolUtil;
    @Autowired
    private JsonUtil jsonUtil;

    public Result<GameDTO> getById(Integer id) {
        Game game = gameMapper.selectById(id);
        if (game == null) {
            return Result.fail(MsgCenter.ERROR_PARAMS);
        }
        List<Integer> tagIds = tagmapperMapper.selectByGame(id);
        List<Tag> tags = null;
        if (tagIds.size() != 0) {
            tags = tagMapper.selectByIds(tagIds);
        }
        List<String> img = imgMapper.selectByGame(game.getId());
        GameDTO res = new GameDTO(game, tags, img);
        return Result.success(res);
    }

    public Result<List<GameDTO>> getRandomGames() {
        // 先从缓存中取数据，如果没有再自动生成
        List<GameDTO> res = jsonUtil.string2Obj(redisPoolUtil.get("everyday"),List.class,GameDTO.class);

        if (res == null || res.size() == 0) {

            List<Game> allgames = gameMapper.selectByStat(Game.STAT_OK);
            int count = allgames.size();
            Set<Integer> numSet = new HashSet<Integer>();
            Random random = new Random();
            List<Game> games = new ArrayList<Game>();
            // 如果游戏数量大于5个就随机取5个，否则取全部的
            if (count > 5) {
                while (numSet.size() < 5) {
                    numSet.add(random.nextInt(count));
                }
                Iterator i = numSet.iterator();
                while (i.hasNext()) {
                    games.add(allgames.get((Integer) i.next()));
                }
            } else {
                games = allgames;
            }
            res = paresGameDTO(games);
            // 将数据存入缓存中
            int tmp = 1000 * 30;
            redisPoolUtil.setEx("everyday", jsonUtil.obj2String(res), tmp);
        }
        return Result.success(res);
    }

    public Result<List<GameDTO>> newestGames() {
        List<GameDTO> res = jsonUtil.string2Obj(redisPoolUtil.get("newestgame"),List.class,GameDTO.class);
        if (res == null || res.size() == 0) {
            List<Game> games = gameMapper.selectByStatOrderByDate(Game.STAT_OK);
            res = paresGameDTO(games);
            redisPoolUtil.setEx("newestgame", jsonUtil.obj2String(res), 60*10);
        }
        return Result.success(res);
    }

    public Result<List<GameDTO>> preUpGames() {
        List<GameDTO> res = jsonUtil.string2Obj(redisPoolUtil.get("preupgames"),List.class,GameDTO.class);
        if (res == null || res.size() == 0) {
            List<Game> games = gameMapper.selectByStatOrderByDate(Game.STAT_PRE);
            res = paresGameDTO(games);
            redisPoolUtil.setEx("preupgames", jsonUtil.obj2String(res), 60*10);
        }
        return Result.success(res);
    }

    public Result<List<GameDTO>> search(String info) {
        List<Integer> kindIds = kindMapper.selectIdByLikeName(info);
        List<Integer> tagIds = tagMapper.selectIdByLikeName(info);
        List<Integer> gameIdsOfKind = null;
        if (kindIds != null && kindIds.size() > 0) {
            gameIdsOfKind = kindmapperMapper.selectBatchByKinds(kindIds);
        }
        List<Integer> gameIdsOfTag = null;
        if (tagIds != null && tagIds.size() > 0) {
            gameIdsOfTag = tagmapperMapper.selectBatchByTags(tagIds);
        }
        Set<Integer> tmpGameIds = new HashSet<Integer>();
        if (gameIdsOfKind != null && gameIdsOfKind.size() > 0) {
            tmpGameIds.addAll(gameIdsOfKind);
        }
        if (gameIdsOfTag != null && gameIdsOfTag.size() > 0) {
            tmpGameIds.addAll(gameIdsOfTag);
        }
        List<Game> games = null;
        if (tmpGameIds.size() > 0) {
            List<Integer> gameIds = new ArrayList<Integer>(tmpGameIds);
            games = gameMapper.selectByIdsAndInfo(gameIds, info);
        } else {
            games = gameMapper.selectByInfo(info);
        }
        return Result.success(paresGameDTO(games));
    }

    public Result getFreeGames() {
        List<Game> games = gameMapper.selectFreeGames();
        return Result.success(paresGameDTO(games));
    }

    public boolean exists(Integer id) {
        return gameMapper.selectById(id) != null;
    }

    private List<GameDTO> paresGameDTO(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<GameDTO>();
        for (Game game : games) {
            List<String> img = imgMapper.selectByGame(game.getId());                // 获取所有的图片
            GameDTO dto = new GameDTO(game, null, img);
            gameDTOS.add(dto);
        }
        return gameDTOS;
    }
}
