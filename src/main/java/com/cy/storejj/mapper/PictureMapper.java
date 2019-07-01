package com.cy.storejj.mapper;

import com.cy.storejj.model.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PictureMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Picture record);

    List<Picture> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    Picture selectByCode(@Param("code")String code);

}