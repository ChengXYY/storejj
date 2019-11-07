package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Question;
import com.cy.storejj.model.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SurveyService {

    JSONObject addSurvey(Survey survey);

    JSONObject editSurvey(Survey survey);

    JSONObject removeSurvey(Integer id);

    JSONObject removeSurvey(String ids);

    List<Survey> getSurveyList(Map<String, Object> filter);

    int getSurveyCount(Map<String, Object> filter);

    Survey getSurvey(Integer id);

    Survey getSurvey(String code);

    // question
    JSONObject addQuestion(Question question);

    JSONObject editQuestion(Question question);

    JSONObject removeQuestion(Integer id);

    List<Question> getQuestionList(Map<String, Object> filter);

    int getQuestionCount(Map<String, Object> filter);

    Question getQuestion(Integer id);

    Question getQuestion(String code);

    //survey-question
    JSONObject bindSurvey(Integer surveyId, Integer questionId);

    JSONObject unbindSurvey(Integer surveyId, Integer questionId);
}
