package com.cy.storejj.version;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionCondition implements RequestCondition<VersionCondition> {

    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");

    private int version;

    public VersionCondition(int version){
        this.version = version;
    }

    @Override
    public VersionCondition combine(VersionCondition versionCondition) {
        return new VersionCondition(versionCondition.getVersion());
    }

    @Override
    public VersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(httpServletRequest.getRequestURI());
        if(m.find()){
            Integer version = Integer.valueOf(m.group(1));
            if(version >= this.version){
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(VersionCondition versionCondition, HttpServletRequest httpServletRequest) {
        //优先匹配最新版本号
        return versionCondition.getVersion() - this.version;
    }

    public int getVersion(){
        return version;
    }
}
