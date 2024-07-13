package com.api.practice.access_service.controller;

import com.api.practice.access_service.dto.UserWhiteList.SpmdtargetWhiteList;
import com.api.practice.access_service.dto.UserWhiteList.WhiteListData;
import com.api.practice.access_service.dto.UserWhiteList.WhiteListMain;
import com.api.practice.access_service.dto.scanData.ScanData;
import com.api.practice.access_service.dto.scanData.ScanMain;
import com.api.practice.access_service.dto.scanData.SpmdtargetScanData;
import com.api.practice.access_service.service.Singleton;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Sets;

import java.util.*;

@RestController
@RequestMapping("/v2")
public class StatsController {

    private ScanMain scanData;
    private WhiteListMain whiteListData;

    public StatsController() {

        this.scanData = Singleton.scanDatagetInstance();
        this.whiteListData = Singleton.whiteListInstance();

    }

    @GetMapping("/getStats")
    public String String() {

        List<ScanData> scanDataList = scanData.getSpmdtargetscan().getScanDataList();
        Set<String> uniquescanDataGpid = new HashSet<>();
        Iterator<ScanData> iterator = scanDataList.iterator();
        while (iterator.hasNext()) {
            uniquescanDataGpid.add(iterator.next().getGpid());
        }


        List<WhiteListData> whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();
        Set<String> uniquewhiteDataGpid = new HashSet<>();
        Iterator<WhiteListData> iterator2 = whiteDataList.iterator();
        while (iterator2.hasNext()) {
            uniquewhiteDataGpid.add(iterator2.next().getGpid());
        }

        Set<String> differenceFromWhiteToScan = Sets.difference(uniquewhiteDataGpid, uniquescanDataGpid);
        Set<String> differenceFromScanToWhite = Sets.difference(uniquescanDataGpid, uniquewhiteDataGpid);
        return "Unique Gipds in WhiteList but not in ScanData: " + differenceFromWhiteToScan.stream().count() + "\n" + "\n"
                + differenceFromWhiteToScan + "\n" + "\n"
                + "Unique Gipds in ScanData but not in WhiteList: " + differenceFromScanToWhite.stream().count() + "\n" + "\n"
                + differenceFromScanToWhite + "\n";

    }

    @GetMapping("/wrongGpids")
    public String wrongGipds() {

        List<ScanData> scanDataList = scanData.getSpmdtargetscan().getScanDataList();
        List<WhiteListData> whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();

        ArrayList<String> wrongWhiteListGipids = new ArrayList<>();
        ArrayList<String> wrongScanGipids = new ArrayList<>();

        scanDataList.forEach(data -> {
            String gpid = data.getGpid();
            if (!gpid.matches("\\d{8}")) {
                wrongScanGipids.add(gpid);
            }
        });


        whiteDataList.forEach(data -> {
            String gpid = data.getGpid();
            if (!gpid.matches("\\d{8}")) {
                wrongWhiteListGipids.add(gpid);
            }
        });


        return " Wrong GIPIDs in WhiteList File : " + wrongWhiteListGipids.size() + "\n" + "\n"
                + wrongWhiteListGipids + "\n" + "\n"
                + " Wrong GIPIDs in ScanData File : " + wrongScanGipids.size() + "\n" + "\n"
                + wrongScanGipids;
    }

    @GetMapping("/getAllScanData")
    public SpmdtargetScanData getAllScanData() {
        return scanData.getSpmdtargetscan();
    }

    @GetMapping("/getAllWhiteListData")
    public SpmdtargetWhiteList getAllWhiteListData() {
        return whiteListData.getSpmdtargetWhiteList();
    }

    @GetMapping("/distictData")
    public String disictData() {

        Set<String> scandata = new HashSet<String>();
        Set<String> whitedata = new HashSet<String>();

        List<ScanData> scanDataList = scanData.getSpmdtargetscan().getScanDataList();
        List<WhiteListData> whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();

        Iterator<ScanData> iterator1 = scanDataList.iterator();
        while (iterator1.hasNext()) {
            iterator1.next().getScanData().forEach(p -> scandata.add(p.toString()));
        }

        Iterator<WhiteListData> iterator2 = whiteDataList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().getModules().forEach(p -> whitedata.add(p.toString()));
        }

        return "ScanData" + "\n" + "\n"
                + scandata + "\n" + "\n"
                + "WhiteList Data" + "\n" + "\n"
                + whitedata;

    }
    ;

    @GetMapping("/duplicategipid")
    public String duplicateGpid() {


        /// Remove Duplicate from Scan///////////////////////////////////

        List<ScanData> scanDataList = scanData.getSpmdtargetscan().getScanDataList();
        ArrayList<String> gpidscan = new ArrayList<>();
        ArrayList<String> duplicateGpidscan = new ArrayList<>();

        Iterator<ScanData> iterator = scanDataList.iterator();
        while (iterator.hasNext()) {
            ScanData scandt = iterator.next();
            if (gpidscan.contains(scandt.getGpid())) {
                duplicateGpidscan.add(scandt.getGpid());
            } else {
                gpidscan.add(scandt.getGpid());
            }
        }

        /// Remove Duplicate from WhiteList///////////////////////////////////

        List<WhiteListData> whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();
        ArrayList<String> gpidwhitelist = new ArrayList<>();
        ArrayList<String> duplicateGpidwhitelist = new ArrayList<>();


        Iterator<WhiteListData> iterator2 = whiteDataList.iterator();
        while (iterator2.hasNext()) {
            WhiteListData whitedt = iterator2.next();
            if (gpidwhitelist.contains(whitedt.getGpid())) {
                duplicateGpidwhitelist.add(whitedt.getGpid());
            } else {
                gpidwhitelist.add(whitedt.getGpid());
            }

        }



        return "There are total  " + duplicateGpidscan.size() + " Duplicate GPIDs from ScanData File." + "\n" + "\n"
                + duplicateGpidscan + "\n" + "\n"
                + "There are total  " + duplicateGpidwhitelist.size() + " Duplicate GPIDs from WhiteList File." + "\n" + "\n"
                + duplicateGpidwhitelist;
    }


    @GetMapping("/countGpid")
    public String countData() {

        List<ScanData> scanDataList = scanData.getSpmdtargetscan().getScanDataList();
        List<WhiteListData> whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();

        List<String> getscanGpidData = new ArrayList<>();
        List<String> getwhiteGpidData = new ArrayList<>();

        scanDataList.iterator().forEachRemaining(p->getscanGpidData.add(p.getGpid()));
        whiteDataList.iterator().forEachRemaining(p->getwhiteGpidData.add(p.getGpid()));

        getscanGpidData.size();

        //getGpidData.addAll(for)


        return "Gpid in Scanfile : " + getscanGpidData.size() + " Gpid in Whitelist file : " + getwhiteGpidData.size();
    }


}