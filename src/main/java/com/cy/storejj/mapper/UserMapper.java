package com.cy.storejj.mapper;

import com.cy.storejj.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    List<User> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    User selectByAccount(@Param("account")String account);
}