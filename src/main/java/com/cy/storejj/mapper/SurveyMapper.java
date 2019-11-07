package com.cy.storejj.mapper;

import com.cy.storejj.model.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SurveyMapper {

    int insertSelective(Survey record);

    int updateByPrimaryKeySelective(Survey survey);

    int deleteByPrimaryKey(Integer id);

    int countByFilter(Map<String, Object> filter);

    List<Survey> selectByFilter(Map<String, Object> filter);

    Survey selectByPrimaryKey(Integer id);

    Survey selectByCode(@Param("code")String code);

    List<Survey> selectByQuestionId(@Param("questionId")Integer questionId);

    int countByQuestionId(@Param("questionId")Integer questionId);

    int bindSurvey(@Param("surveyId") Integer surveyId, @Param("questionId")Integer questionId);

    int unbindSurvey(@Param("surveyId") Integer surveyId, @Param("questionId")Integer questionId);
}