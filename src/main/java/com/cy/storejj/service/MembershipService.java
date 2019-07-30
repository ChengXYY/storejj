package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Membership;

import java.util.List;

public interface MembershipService {
    //add membership
    JSONObject add(Membership membership);
    //update membership
    JSONObject edit(Membership membership);
    //delete membership
    JSONObject remove(Integer id);

    Membership get(Integer id);

    List<Membership> getListAll();

    Membership getByLevel(Integer level);


}
