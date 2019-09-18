package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.SuggestionMapper;
import com.cy.storejj.model.Suggestion;
import com.cy.storejj.service.SuggestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuggestionServiceImpl extends AdminConfig implements SuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;

    @Override
    public JSONObject add(Suggestion suggestion) {
        if (StringUtils.isBlank(suggestion.getUserName())
                || (StringUtils.isBlank(suggestion.getMobile())
                && (StringUtils.isBlank(suggestion.getEmail()) || !checkEmail(suggestion.getEmail())))
                || StringUtils.isBlank(suggestion.getContent())) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs = suggestionMapper.insertSelective(suggestion);
        if(rs > 0){
            return success(suggestion.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Suggestion suggestion) {
        if(!checkId(suggestion.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Suggestion s = get(suggestion.getId());
        if(s == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        int rs = suggestionMapper.updateByPrimaryKeySelective(suggestion);
        if(rs >= 0){
            return success(suggestion.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        return null;
    }

    @Override
    public JSONObject remove(String ids) {
        return null;
    }

    @Override
    public List<Suggestion> getList(Map<String, Object> filter) {
        return suggestionMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return suggestionMapper.countByFilter(filter);
    }

    @Override
    public Suggestion get(Integer id) {
        if(!checkId(id)) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        return suggestionMapper.selectByPrimaryKey(id);

    }
}
