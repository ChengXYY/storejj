package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.ArticleMapper;
import com.cy.storejj.model.Article;
import com.cy.storejj.service.ArticleService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ArticleServiceImpl extends AdminConfig implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public JSONObject add(Article article) {
        if(StringUtils.isBlank(article.getCode()))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //判断重复
        Article art = get(article.getCode());
        if(art!=null) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = articleMapper.insertSelective(article);
        if(rs > 0){
            return success(article.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Article article) {
        if(!checkId(article.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //判断重复
        Article art = get(article.getCode());
        if(art!=null && art.getId()!=article.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = articleMapper.updateByPrimaryKeySelective(article);
        if(rs > 0){
            return success(article.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Article article = get(id);
        if(article.getImageUrl()!=null){
            //删除图片
            article.getImageUrl().forEach(r->{
                String url = r.replace("/getimg?filename=", "");
                removeFile(url);
            });
        }
        int rs = articleMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return success(id);
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
            return fail(msg);
        }else {
            return success(msg);
        }
    }

    @Override
    public List<Article> getList(Map<String, Object> filter) {
        List<Article> list = articleMapper.selectByFilter(filter);
        if(list !=null ){
            for (int i=0; i<list.size(); i++){
                List<String> imgSrc = getImgSrc(list.get(i).getContent());
                list.get(i).setImageUrl(imgSrc);

                String intro = getText(list.get(i).getContent());
                if(intro.length() > 100){
                    intro = intro.substring(0,100)+"...";
                }
                list.get(i).setIntro(intro);
            }
        }
        return list;
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return articleMapper.countByFilter(filter);
    }

    @Override
    public Article get(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        Article article =  articleMapper.selectByPrimaryKey(id);
        if(article!=null){
            List<String> imgSrc = getImgSrc(article.getContent());
            article.setImageUrl(imgSrc);
        }
        return article;
    }

    @Override
    public Article get(String code) {
        if(code == null || code.isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);

        Article article =  articleMapper.selectByCode(code);
        if(article!=null){
            List<String> imgSrc = getImgSrc(article.getContent());
            article.setImageUrl(imgSrc);
        }
        return article;
    }
}
