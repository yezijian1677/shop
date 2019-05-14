package cur.pro.mapper;

import cur.pro.entity.Ordermapper;

import java.util.List;

public interface OrdermapperMapper {
    int insert(Ordermapper record);

    List<Integer> selectByOrder(Integer order);
}