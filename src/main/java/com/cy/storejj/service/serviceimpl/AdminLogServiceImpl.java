package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.AdminLogMapper;
import com.cy.storejj.model.AdminLog;
import com.cy.storejj.service.AdminLogService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Service
public class AdminLogServiceImpl extends AdminConfig implements AdminLogService {

    @Autowired
    private AdminLogMapper adminlogMapper;

    @Override
    public JSONObject add(HttpSession session, String content) {
        if(content.isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        String account = "";
        if(session.getAttribute(adminAccount)!=null){
            account = session.getAttribute(adminAccount).toString();
        }
        if(session.getAttribute(userAccount)!=null){
            account = session.getAttribute(userAccount).toString();
        }
        if(StringUtils.isBlank(account)) throw JsonException.newInstance(ErrorCodes.ACCOUNT_NOT_EMPTY);
        AdminLog log = new AdminLog();
        log.setContent(content);
        log.setAdmin(account);
        log.setCreateBy(session.getAttribute(adminAccount).toString());
        int rs =  adminlogMapper.insertSelective(log);
        if (rs >0)
            return CommonOperation.success();
        else
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
    }

    @Override
    public List<AdminLog> getList(Map<String, Object> filter) {
        return adminlogMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return adminlogMapper.countByFilter(filter);
    }
}
