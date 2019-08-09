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
        if(membership.getMinPoints() == -1 || (membership.getMaxPoints()!=-1 && membership.getMinPoints()>membership.getMaxPoints()) || membership.getLevel()<0)
            throw JsonException.newInstance(ErrorCodes.PARAM_NOT_LEGAL);
        List<Membership> mList = membershipMapper.selectAll();
        for (int i=0; i<mList.size(); i++){
            //等级重复
            if(membership.getLevel() == mList.get(i).getLevel()){
                throw JsonException.newInstance(ErrorCodes.ITEM_REPEATED);
            }
            //积分交叉
            //最高积分>等级高的最低积分
            if(membership.getLevel() < mList.get(i).getLevel() && membership.getMaxPoints() > mList.get(i).getMinPoints()){
                throw JsonException.newInstance(ErrorCodes.POINTS_CROSS_HIGN);
            }
            //最低积分<等级低的最高积分
            if(membership.getLevel() > mList.get(i).getLevel() && membership.getMinPoints() < mList.get(i).getMaxPoints()){
                throw JsonException.newInstance(ErrorCodes.POINTS_CROSS_LOW);
            }
        }

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
        List<Membership> mList = membershipMapper.selectAll();

        for (int i=0; i<mList.size(); i++){
            if(membership.getLevel()!=null){
                //等级重复
                if(membership.getLevel() == mList.get(i).getLevel() && mList.get(i).getId()!=membership.getId()){
                    throw JsonException.newInstance(ErrorCodes.LEVEL_REPEATED);
                }

            }
            //积分交叉
            //最高积分>等级高的最低积分
            if(membership.getLevel() < mList.get(i).getLevel() && membership.getMaxPoints() > mList.get(i).getMinPoints()){
                throw JsonException.newInstance(ErrorCodes.POINTS_CROSS_HIGN);
            }
            //最低积分<等级低的最高积分
            if(membership.getLevel() > mList.get(i).getLevel() && membership.getMinPoints() < mList.get(i).getMaxPoints()){
                throw JsonException.newInstance(ErrorCodes.POINTS_CROSS_LOW);
            }
        }

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

    @Override
    public Membership getByLevel(Integer level) {
        if(level == null) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        return membershipMapper.selectByLevel(level);
    }
}
