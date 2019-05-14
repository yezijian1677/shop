package cur.pro.mapper;

import cur.pro.entity.Code;

public interface CodeMapper {

    int insert(Code record);

    Code selectById(Integer id);

    int update(Code record);

}