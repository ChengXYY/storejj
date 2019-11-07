package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Question;
import com.cy.storejj.model.QuestionOption;
import com.cy.storejj.model.Survey;
import com.cy.storejj.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Permission("8000")
@Controller
@RequestMapping("/survey")
public class SurveyController extends AdminConfig {

    @Autowired
    private SurveyService surveyService;

    @Permission("8101")
    @RequestMapping("/list")
    public String surveyList(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = surveyService.getSurveyCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);

        List<Survey> list = surveyService.getSurveyList(param);

        model.addAllAttributes(param);

        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+surveyMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "survey");
        model.addAttribute("LeftMenuFlag", "survey");
          return adminHtml +"survey_list";
    }

    @Permission("8102")
    @RequestMapping("/add")
    public String addSurvey(ModelMap model){
        //questionList
        Map<String, Object> filter = new HashMap<>();
        setPagenation(filter);
        List<Question> questionList = surveyService.getQuestionList(filter);
        //获取模板列表
        model.addAttribute("pageTitle",addPageTitle+surveyMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "survey");
        model.addAttribute("LeftMenuFlag", "survey");
        return adminHtml+"survey_add";
    }

    @Permission("8102")
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addSurvey(Survey survey, HttpSession session){
        try {
            survey.setCreateBy(session.getAttribute(adminAccount).toString());
            survey.setUpdateBy(session.getAttribute(adminAccount).toString());
            return surveyService.addSurvey(survey);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("8103")
    @RequestMapping("/edit")
    public String editSurvey(@RequestParam(value = "id", required = true)Integer id, ModelMap model){
        try {

            Survey survey = surveyService.getSurvey(id);
            model.addAttribute("survey", survey);
            model.addAttribute("pageTitle",editPageTitle+surveyMenuTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "survey");
            model.addAttribute("LeftMenuFlag", "survey");

            return adminHtml +"survey_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("8103")
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject editSurvey(Survey survey, HttpSession session){
        try{
            survey.setUpdateBy(session.getAttribute(adminAccount).toString());
            return surveyService.editSurvey(survey);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("8201")
    @RequestMapping(value = "/question/list", method = RequestMethod.GET)
    public String questionList(@RequestParam Map<String, Object> param,
                               HttpServletRequest request,
                               ModelMap model){
        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = surveyService.getQuestionCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);

        List<Question> list = surveyService.getQuestionList(param);

        model.addAllAttributes(param);

        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+questionMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "survey");
        model.addAttribute("LeftMenuFlag", "question");
        return adminHtml+"question_list";
    }

    @Permission("8202")
    @RequestMapping("/question/add")
    public String addQuestion(ModelMap model){
        //获取模板列表
        model.addAttribute("pageTitle",addPageTitle+questionMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "survey");
        model.addAttribute("LeftMenuFlag", "survey");
        return adminHtml+"question_add";
    }

    @Permission("8202")
    @RequestMapping(value = "/question/add/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addQuestion(Question question, HttpSession session){
        try {
            //参数处理
            List<QuestionOption> optionList = question.getOptions();
            if(optionList != null && optionList.size() > 0){
                for (int i=0; i<optionList.size(); i++){
                    if(optionList.get(i).getIsRight() == null){
                        optionList.get(i).setIsRight(0);
                    }
                    if (optionList.get(i).getIsText() == null){
                        optionList.get(i).setIsText(0);
                    }
                }
            }
            question.setCreateBy(session.getAttribute(adminAccount).toString());
            return surveyService.addQuestion(question);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("8203")
    @RequestMapping("/question/edit")
    public String editQuestion(@RequestParam(value = "id", required = true)Integer id, ModelMap model){
        try {

            Question question = surveyService.getQuestion(id);
            model.addAttribute("question", question);
            model.addAttribute("pageTitle",editPageTitle+questionMenuTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "survey");
            model.addAttribute("LeftMenuFlag", "question");

            return adminHtml +"question_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("8203")
    @RequestMapping(value = "/question/edit/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject editQuestion(Question question, HttpSession session){
        try{
            return surveyService.editQuestion(question);
        }catch (JsonException e){
            return e.toJson();
        }
    }

}
