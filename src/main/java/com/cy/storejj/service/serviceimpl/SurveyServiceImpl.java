package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.QuestionMapper;
import com.cy.storejj.mapper.SurveyMapper;
import com.cy.storejj.model.Question;
import com.cy.storejj.model.Survey;
import com.cy.storejj.service.SurveyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SurveyServiceImpl extends AdminConfig implements SurveyService {
    @Autowired
    private SurveyMapper surveyMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public JSONObject addSurvey(Survey survey) {
        if(StringUtils.isBlank(survey.getCode()) || StringUtils.isBlank(survey.getName()))
            throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs = surveyMapper.insertSelective(survey);
        if(rs > 0){
            return success(survey.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject editSurvey(Survey survey) {
        if(survey.getId()==null && StringUtils.isBlank(survey.getCode()))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = surveyMapper.updateByPrimaryKeySelective(survey);
        if(rs > 0){
            return success(survey.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject removeSurvey(Integer id) {
        if(id == null) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = surveyMapper.deleteByPrimaryKey(id);
        if(rs > 0){
            return success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject removeSurvey(String ids) {
        return null;
    }

    @Override
    public List<Survey> getSurveyList(Map<String, Object> filter) {
        List<Survey> surveyList = surveyMapper.selectByFilter(filter);
        if(surveyList!=null && surveyList.size()>0){
            for (int i=0; i<surveyList.size(); i++){
                int qCount = questionMapper.countBySurveyId(surveyList.get(i).getId());
                surveyList.get(i).setQuestionCount(qCount);
            }
        }
        return surveyList;
    }

    @Override
    public int getSurveyCount(Map<String, Object> filter) {
        return surveyMapper.countByFilter(filter);
    }

    @Override
    public Survey getSurvey(Integer id) {
        if(id == null) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Survey survey = surveyMapper.selectByPrimaryKey(id);
        if(survey !=null){
            List<Question> questionList = questionMapper.selectBySurveyId(id);
            survey.setQuestionList(questionList);
        }
        return survey;
    }

    @Override
    public Survey getSurvey(String code) {
        if(StringUtils.isBlank(code))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        Survey survey = surveyMapper.selectByCode(code);
        if(survey !=null){
            List<Question> questionList = questionMapper.selectBySurveyId(survey.getId());
            survey.setQuestionList(questionList);
            survey.setQuestionCount(questionList.size());
        }
        return survey;
    }

    @Override
    public JSONObject addQuestion(Question question) {
        if(StringUtils.isBlank(question.getCode())
                ||StringUtils.isBlank(question.getContent()) )throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs = questionMapper.insertSelective(question);
        if(rs > 0){
            return success(question.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject editQuestion(Question question) {
        return null;
    }

    @Override
    public JSONObject removeQuestion(Integer id) {
        return null;
    }

    @Override
    public List<Question> getQuestionList(Map<String, Object> filter) {
        List<Question> questionList = questionMapper.selectByFilter(filter);
        if(questionList !=null && questionList.size()>0){
            for (int i=0; i<questionList.size(); i++){
                int sCount = surveyMapper.countByQuestionId(questionList.get(i).getId());
                questionList.get(i).setSurveyCount(sCount);
            }
        }
        return questionList;
    }

    @Override
    public int getQuestionCount(Map<String, Object> filter) {
        return surveyMapper.countByFilter(filter);
    }

    @Override
    public Question getQuestion(Integer id) {
        if(id == null) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question !=null){
            List<Survey> surveyList = surveyMapper.selectByQuestionId(question.getId());
            question.setSurveyList(surveyList);
            question.setSurveyCount(surveyList.size());
        }
        return question;
    }

    @Override
    public Question getQuestion(String code) {
        return null;
    }

    @Override
    public JSONObject bindSurvey(Integer surveyId, Integer questionId) {
        return null;
    }

    @Override
    public JSONObject unbindSurvey(Integer surveyId, Integer questionId) {
        return null;
    }
}
