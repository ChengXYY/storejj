package com.cy.storejj.mapper;

import com.cy.storejj.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QuestionMapper {
    int insertSelective(Question record);

    int updateByPrimaryKeySelective(Question record);

    int deleteByPrimaryKey(Question record);

    int countByFilter(Map<String, Object> filter);

    List<Question> selectByFilter(Map<String, Object> filter);

    Question selectByPrimaryKey(Integer id);

    Question selectByCode(@Param("code")String code);

    List<Question> selectBySurveyId(@Param("surveyId")Integer surveyId);

    int countBySurveyId(@Param("surveyId")Integer surveyId);

    //survey-question
    int insertSQ(@Param("surveyId") Integer surveyId, @Param("questionId") Integer questionId);

    int deleteSQ(@Param("surveyId") Integer surveyId, @Param("questionId") Integer questionId);
}