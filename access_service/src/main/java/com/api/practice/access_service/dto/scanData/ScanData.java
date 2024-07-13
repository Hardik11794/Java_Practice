package com.api.practice.access_service.dto.scanData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScanData {

    @JsonProperty("gpid")
    private String gpid;

    @JsonProperty("scanData")
    private List<String> scanData;

    public List<String> getScanData() {
        return scanData;
    }

    public void setScanData(List<String> scanData) {
        this.scanData = scanData;
    }

    public String getGpid() {
        return gpid;
    }

    public void setGpid(String gpid) {
        this.gpid = gpid;
    }
}
