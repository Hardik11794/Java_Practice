package com.api.practice.access_service.dto.scanData;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpmdtargetScanData {

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

    @JsonProperty("scanDataList")
    private List<ScanData> scanDataList;

   /* public String getDocVrsn() {
        return DocVrsn;
    }

    public void setDocVrsn(String DocVrsn) {
        this.DocVrsn = DocVrsn;
    }

    public String getMdfdTmstmp() {
        return MdfdTmstmp;
    }

    public void set$MdfdTmstmp(String MdfdTmstmp) {
        this.MdfdTmstmp = MdfdTmstmp;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
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

    public List<ScanData> getScanDataList() {
        return scanDataList;
    }

    public void setScanDataList(List<ScanData> scanDataList) {
        this.scanDataList = scanDataList;
    }
}
