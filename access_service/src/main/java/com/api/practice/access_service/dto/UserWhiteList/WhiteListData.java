package com.api.practice.access_service.dto.UserWhiteList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WhiteListData {

    @JsonProperty("gpid")
    private String gpid;

    @JsonProperty("modules")
    private List<String> modules;


    public String getGpid() {
        return gpid;
    }

    public void setGpid(String gpid) {
        this.gpid = gpid;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }
}
