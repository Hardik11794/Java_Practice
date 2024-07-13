package com.api.practice.access_service.dto.UserWhiteList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WhiteListMain {

    @JsonProperty("sp-md-target")
    private SpmdtargetWhiteList spmdtargetWhiteList;

    public SpmdtargetWhiteList getSpmdtargetWhiteList() {
        return spmdtargetWhiteList;
    }

    public void setSpmdtargetWhiteList() {
        this.spmdtargetWhiteList = spmdtargetWhiteList;
    }
}
