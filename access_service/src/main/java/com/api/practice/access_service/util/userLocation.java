package com.api.practice.access_service.util;

import com.api.practice.access_service.dto.UserWhiteList.WhiteListData;

import java.util.List;

public class userLocation {

    public static String loc(List<WhiteListData> whiteDataList,String gpid) {
        List<String> whitelist = null;
        for (WhiteListData whiteDataListItem : whiteDataList) {
            if (whiteDataListItem.getGpid().equals(gpid)) {
                whitelist = whiteDataListItem.getModules();
                if ((whitelist.contains("Scan") && whitelist.contains("Scan_CA"))) {
                    return "USCA";
                }
                if ((whitelist.contains("Scan") && !whitelist.contains("Scan_CA"))) {
                    return "US";
                }
                if ((!whitelist.contains("Scan") && whitelist.contains("Scan_CA"))) {
                    return "CA";
                }
            }
        }
        return "No_Change";
    }
}

