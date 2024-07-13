package com.api.practice.access_service.dto.UserWhiteList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpmdtargetWhiteList{

    @JsonProperty("$DocVrsn")
    private String DocVrsn;

    @JsonProperty("$MdfdTmstmp")
    private String MdfdTmstmp;

    @JsonProperty("$Type")
    private String Type;

    @JsonProperty("CrtdDtmStmp")
    private String CrtdDtmStmp;

    @JsonProperty("Id")
    private String Id;

    @JsonProperty("UserWhiteList")
    private List<WhiteListData> userWhiteListData;

   /* public String getDocVrsn() {
        return DocVrsn;
    }

    public void setDocVrsn(String docVrsn) {
        DocVrsn = docVrsn;
    }

    public String getMdfdTmstmp() {
        return MdfdTmstmp;
    }

    public void setMdfdTmstmp(String mdfdTmstmp) {
        MdfdTmstmp = mdfdTmstmp;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCrtdDtmStmp() {
        return CrtdDtmStmp;
    }

    public void setCrtdDtmStmp(String crtdDtmStmp) {
        CrtdDtmStmp = crtdDtmStmp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }*/

    public List<WhiteListData> getUserWhiteListData() {
        return userWhiteListData;
    }

    public void setUserWhiteListData(List<WhiteListData> userWhiteListData) {
        this.userWhiteListData = userWhiteListData;
    }
}
