package com.api.practice.access_service.service;

import com.api.practice.access_service.dto.UserWhiteList.WhiteListMain;
import com.api.practice.access_service.dto.scanData.ScanMain;
import com.api.practice.access_service.dto.scanData.SpmdtargetScanData;
import com.api.practice.access_service.util.JSONutility;

public class Singleton {


    private static String scanfilePath = "access_service/ScanDat.json";
    private static String userWhiteListPath = "access_service/UsrWhtLstng.json";

    private static JSONutility scanDataObject = new JSONutility(scanfilePath, ScanMain.class);
    private static JSONutility whiteListDataObject = new JSONutility(userWhiteListPath, WhiteListMain.class);
    private static ScanMain scanData;


    private static WhiteListMain whiteListData;


    private Singleton() {

    }



    public static ScanMain scanDatagetInstance() {
        if (scanData == null) {
            scanData = (ScanMain) scanDataObject.getData(); // Create the instance if not already created
        }
        return scanData;
    }



    public static WhiteListMain whiteListInstance() {
        if (whiteListData == null) {
            whiteListData = (WhiteListMain) whiteListDataObject.getData(); // Create the instance if not already created
        }
        return whiteListData;
    }

}


