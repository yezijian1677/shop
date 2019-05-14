package cur.pro.mapper;

import cur.pro.entity.Tagmapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagmapperMapper {
    int insert(Tagmapper record);

    List<Integer> selectByTag(Integer tag);

    List<Integer> selectByGame(Integer game);

    List<Integer> selectBatchByTags(@Param(value = "tags") List<Integer> tags);
}