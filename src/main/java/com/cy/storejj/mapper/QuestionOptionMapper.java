package com.cy.storejj.mapper;

import com.cy.storejj.model.QuestionOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionOptionMapper {

    int insertSelective(QuestionOption record);

    List<QuestionOption> selectByQuestionId(@Param("questionId")Integer questionId);

    int countByQuestionId(@Param("questionId")Integer questionId);

    int deleteByQuestionId(@Param("questionId")Integer questionId);
}