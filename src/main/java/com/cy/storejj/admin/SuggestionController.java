package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Suggestion;
import com.cy.storejj.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/suggestion")
@Permission("7000")
public class SuggestionController extends AdminConfig {

    @Autowired
    private SuggestionService suggestionService;

    @Permission("7100")
    @RequestMapping(value = {"/", "/index","/list","" },method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();

        currentUrl = handleParam(param, currentUrl);

        param.put("currentUrl", currentUrl);

        int totalCount = suggestionService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Suggestion> list = suggestionService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+suggestionMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "suggestion");
        return adminHtml+"suggestion_list";
    }

    @Permission("7200")
    @ResponseBody
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public JSONObject handle(@RequestParam("id")Integer id, HttpSession session){
        try{
            Suggestion suggestion = new Suggestion();
            suggestion.setId(id);
            suggestion.setStatus(1);
            suggestion.setUpdateBy(session.getAttribute(adminAccount).toString());
            return suggestionService.edit(suggestion);

        }catch (JsonException e){
            return e.toJson();
        }
    }
}
