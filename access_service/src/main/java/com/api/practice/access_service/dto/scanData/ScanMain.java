package com.api.practice.access_service.dto.scanData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScanMain {

   @JsonProperty("sp-md-target")
    private SpmdtargetScanData spmdtargetscan;

    public SpmdtargetScanData getSpmdtargetscan() {
        return spmdtargetscan;
    }

/*
    public void setSpmdtargetscan(SpmdtargetScanData spmdtargetscan) {
        this.spmdtargetscan = spmdtargetscan;
    }*/
}
