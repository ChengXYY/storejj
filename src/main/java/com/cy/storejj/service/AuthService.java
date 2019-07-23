package com.cy.storejj.service;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Map<String, Object>> getAuthTree();

    String getAuthStr();
}
