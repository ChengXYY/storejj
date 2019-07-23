package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.MembershipMapper;
import com.cy.storejj.model.Membership;
import com.cy.storejj.service.MembershipService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl extends AdminConfig implements MembershipService {
    @Autowired
    private MembershipMapper membershipMapper;

    @Override
    public JSONObject add(Membership membership) {
        //参数检查
        if(StringUtils.isBlank(membership.getTitle())
                || membership.getMinPoints() == null
                || membership.getMaxPoints()==null
                || membership.getLevel() == null)
            throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        if(membership.getMinPoints()>membership.getMaxPoints() || membership.getLevel()<0)
            throw JsonException.newInstance(ErrorCodes.PARAM_NOT_LEGAL);
        Membership m = membershipMapper.selectByLevel(membership.getLevel());
        if(m !=null)
            throw JsonException.newInstance(ErrorCodes.ITEM_REPEATED);

        int rs = membershipMapper.insertSelective(membership);
        if(rs > 0){
            return CommonOperation.success(membership.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }

    }

    @Override
    public JSONObject edit(Membership membership) {
        if(!CommonOperation.checkId(membership.getId()))
            throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Membership m = get(membership.getId());
        if(m == null)
            throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);

        int rs = membershipMapper.updateByPrimaryKeySelective(membership);
        if(rs > 0){
            return CommonOperation.success(membership.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id))
            throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = membershipMapper.deleteByPrimaryKey(id);
        if(rs > 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public Membership get(Integer id) {
        if(!CommonOperation.checkId(id))
            throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        return membershipMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Membership> getListAll() {
        return membershipMapper.selectAll();
    }
}
