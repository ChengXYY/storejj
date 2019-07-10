package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.SitePageMapper;
import com.cy.storejj.model.SitePage;
import com.cy.storejj.service.PageTplService;
import com.cy.storejj.service.SitePageService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SitePageServiceImpl implements SitePageService {

    @Autowired
    private SitePageMapper sitepageMapper;

    @Autowired
    private PageTplService pagetplService;

    @Override
    public JSONObject add(SitePage sitepage) {
        if((!CommonOperation.checkId(sitepage.getTplId()) && (sitepage.getContent()!=null || !sitepage.getContent().isEmpty()))
                || sitepage.getCode().isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        if(sitepage.getTplId() > 0){
            pagetplService.get(sitepage.getTplId());
        }
        //判断重复
        SitePage page = get(sitepage.getCode());
        if(page!=null) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);

        int rs = sitepageMapper.insertSelective(sitepage);
        if(rs > 0){
            return CommonOperation.success(sitepage.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(SitePage sitepage) {
        if(!CommonOperation.checkId(sitepage.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        if((sitepage.getTplId() == 0 && (sitepage.getContent()!=null || !sitepage.getContent().isEmpty()))
            || sitepage.getCode().isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        if(sitepage.getTplId() > 0){
            pagetplService.get(sitepage.getTplId());
        }
        //判断重复
        SitePage page = get(sitepage.getCode());
        if(page!=null && page.getId()!=sitepage.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);

        int rs = sitepageMapper.updateByPrimaryKeySelective(sitepage);

        if(rs > 0){
            return CommonOperation.success(sitepage.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = sitepageMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(String ids) {
        if(ids == null || ids.isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        ids = ids.replace(" ", "");
        String[] idArr = ids.split(",");
        String msg = "";
        int success = 0;
        int count = 0;
        int fail = 0;
        for(String id : idArr){
            count ++;
            try {
                remove(Integer.parseInt(id));
                success++;
            }catch (JsonException e){
                msg += "ID"+id+"："+ e.getMsg()+ "。";
                fail++;
            }
        }
        msg = "成功删除："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return CommonOperation.fail(msg);
        }else {
            return CommonOperation.success(msg);
        }
    }

    @Override
    public List<SitePage> getList(Map<String, Object> filter) {
         return sitepageMapper.selectByFilter(filter);
    }

    @Override
    public int getCount(Map<String, Object> filter) {
        return sitepageMapper.countByFilter(filter);
    }

    @Override
    public SitePage get(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        return sitepageMapper.selectByPrimaryKey(id);
    }

    @Override
    public SitePage get(String code) {
        if(code ==null || code.isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        return sitepageMapper.selectByCode(code);
    }
}
