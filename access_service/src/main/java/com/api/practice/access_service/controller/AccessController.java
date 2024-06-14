package com.api.practice.access_service.controller;

import com.api.practice.access_service.dto.UserWhiteList.WhiteListData;
import com.api.practice.access_service.dto.UserWhiteList.WhiteListMain;
import com.api.practice.access_service.dto.scanData.ScanData;
import com.api.practice.access_service.dto.scanData.ScanMain;

import com.api.practice.access_service.service.Singleton;
import com.api.practice.access_service.util.userLocation;

import com.opencsv.exceptions.CsvException;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class AccessController {

    private ScanMain scanData;
    private WhiteListMain whiteListData;


    private List<ScanData> scanDataList;

    private List<WhiteListData> whiteDataList;




    public AccessController() {



        this.scanData = Singleton.scanDatagetInstance();

        this.whiteListData = Singleton.whiteListInstance();

        this.whiteDataList = whiteListData.getSpmdtargetWhiteList().getUserWhiteListData();

        this.scanDataList = scanData.getSpmdtargetscan().getScanDataList();
    }




    @PutMapping("/postAllScanData")
    public List<ScanData> changeAllScanData() {


        //PGT values for US and CA//////////////////////////////////////////////////////////
        List<String> chngDataBoth = new ArrayList<>();
        String[] dataBoth = {
                "1000027190", "1000019036", "1000036962", "1000037498",
                "1000018265", "1000042333", "1000027118", "1000041253",
                "1000041227", "1000027162", "1000029709", "1000019208",
                "1000032503", "1000018803", "1000038132", "1000033122",
                "1000019139", "1000030772", "1000041303", "1000042303",
                "1000018990"
        };
        chngDataBoth.addAll(List.of(dataBoth));

        //PGT values for US /////////////////////////////////////

        List<String> chngDataUS = new ArrayList<>();
        String[] dataUS = {
                "1000027190", "1000019036", "1000036962", "1000037498",
                "1000018265", "1000042333", "1000027118", "1000041253",
                "1000041227", "1000027162", "1000029709", "1000019208",
                "1000032503", "1000018803", "1000038132", "1000033122",
                "1000019139", "1000030772", "1000041303", "1000018990"
        };
        chngDataUS.addAll(List.of(dataUS));

        //PGT values for CA ///////////////////

        List<String> chngDataCA = new ArrayList<>();
        String[] dataCA = {"1000042303"};
        chngDataCA.addAll(List.of(dataCA));


        //Replace ScanData ///////////////////

        for (ScanData scandt : scanDataList) {
            if (userLocation.loc(whiteDataList, scandt.getGpid()) == "USCA") {
                scandt.setScanData(chngDataBoth);
            }
            if (userLocation.loc(whiteDataList, scandt.getGpid()) == "CA") {
                scandt.setScanData(chngDataCA);
            }
            if (userLocation.loc(whiteDataList, scandt.getGpid()) == "US") {
                scandt.setScanData(chngDataUS);
            }

        }


        return scanDataList;
    }

    @PutMapping("/giveWhiteListAccess")
    public String giveWhitelistaccess(@RequestParam(required = false) @Pattern(regexp = "^Scan$") String scan,
                                      @RequestParam(required = false) @Pattern(regexp = "^Scan_CA$") String scan_ca,
                                      @RequestParam(required = false) @Pattern(regexp = "^SalesExecution$") String salesececution,
                                      @RequestParam(required = false) @Pattern(regexp = "^SalesExecution_CA$") String salesececution_ca,
                                      @RequestParam(required = false) @Pattern(regexp = "^RestrictedChain$") String restrictedchain,
                                      @RequestParam(required = false) @Pattern(regexp = "^UnrestrictedChain$") String unrestrictedchain,
                                      @RequestParam(required = false) @Pattern(regexp = "^UnrestrictedChain_CA$") String unrestrictedchain_ca,
                                      @RequestParam(required = false) @Pattern(regexp = "^Ordering$") String ordering,
                                      @RequestParam(required = false) @Pattern(regexp = "^ViewOnly$") String viewonly,
                                      @RequestParam(required = false) @Pattern(regexp = "^ViewOnly_CA$") String viewonly_ca,
                                      @RequestParam(required = false) @Pattern(regexp = "^SalesLeadModule$") String slmodule,
                                      @RequestParam(required = false) @Pattern(regexp = "^RestrictedChain_CA$") String RestrictedChain_CA,
                                      @RequestParam(required = false) @Pattern(regexp = "^SLImpersonation$") String slimporsonate,
                                      @RequestParam(required = false) @Pattern(regexp = "^GeoHierarchyDrilldown$") String geoDrilldown,
                                      @RequestBody String gpids) {

        String[] lines = gpids.split(System.lineSeparator());
        List<String> gpidList = Arrays.asList(lines).stream().filter(s -> s.matches("\\d{8}")) // Matches exactly six digits
                .collect(Collectors.toList());

        //////Add all modules from request

        ArrayList<String> modules = new ArrayList<>();
        modules.addAll(Arrays.asList(geoDrilldown, ordering, salesececution, scan, restrictedchain, unrestrictedchain, salesececution_ca, unrestrictedchain_ca, RestrictedChain_CA, scan_ca, slmodule, viewonly_ca, viewonly, slimporsonate));
        modules.removeIf(Objects::isNull);

        System.out.println("First " + modules);

        ///////////////////////////////

        for (String gpidfrombody : gpidList) {
            WhiteListData data = new WhiteListData();
            data.setGpid(gpidfrombody);
            data.setModules(modules);
            whiteDataList.add(data);

            if (scan != null || scan_ca != null) {
                ScanData sdata = new ScanData();
                sdata.setGpid(gpidfrombody);
                scanDataList.add(sdata);

            }

        }


            return "Below Modules are " + "\n" + "\n"
                    + modules + "\n" + "\n"
                    + " given to  " + "\n" + "\n"
                    + gpidList;

        }

    @GetMapping("/getAllData")
    public String getAllData(@RequestParam String id){

        String gpid = id;
        String name = null;
        String hr_status = null;
        String email = null;
        List<String> scandata = null;
        List<String> whitelist = null;
        String watsrole = null;
        String hhnextwebapprole = null;

        for(ScanData scandataitem : scanDataList){
            if(scandataitem.getGpid().equals(gpid)){
                scandata = scandataitem.getScanData();
        }


            for(WhiteListData whiteDataListItem : whiteDataList){
                if(whiteDataListItem.getGpid().equals(gpid)){
                    whitelist = whiteDataListItem.getModules();
                }
            }

        };

        try (CSVReader reader = new CSVReader(new FileReader("api_practice/WATS_UserReport 9.csv"))) {
            List<String[]> records = reader.readAll();


            for(String[] record : records){
                if(record[0].equals(gpid)) {
                    name = record[1] + " " + record[2];
                    hr_status = record[3];
                    email = record[4];
                    watsrole = record[6];
                    hhnextwebapprole = record[27];
                }
            };

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }


        return "GPID--- : "  +gpid  + "\n"
                + "Name--- : "  + name + "\n"
                + "HR Status--- : " +  hr_status + "\n"
                + "Email--- : "  + email + "\n"
                + "Scandata--- : " + scandata + "\n"
                + "WhiteList--- : " + whitelist + "\n"
                + "WatsRoles--- : " + watsrole + "\n"
                + "HHnextWebAppRoles--- : " + hhnextwebapprole + "\n" ;

    }



    @PutMapping("/modifyWhiteData")
    public String modify() {


        // First block: Populate the data map
        HashMap<String, List<String>> data = new HashMap<>();
        for (WhiteListData wl : whiteDataList) {
            data.computeIfAbsent(wl.getGpid(), k -> new ArrayList<>()).addAll(wl.getModules());
        }

        // Second block: Modify the modules based on conditions
        for (WhiteListData data1 : whiteDataList) {
            List<String> tempData1 = data.get(data1.getGpid());
            List<String> modified = new ArrayList<>();

            if (tempData1.contains("GeoHierarchyDrilldown")) {
                modified.add("GeoHierarchyDrilldown");
            }
            if (tempData1.contains("Ordering")) {
                modified.add("Ordering");
            }
            if (tempData1.contains("SalesExecution")) {
                modified.add("SalesExecution");
            }
            if (tempData1.contains("Scan")) {
                modified.add("Scan");
            }
            if (tempData1.contains("RestrictedChain")) {
                modified.add("RestrictedChain");
            }
            if (tempData1.contains("UnrestrictedChain")) {
                modified.add("UnrestrictedChain");
            }
            if (tempData1.contains("ViewOnly")) {
                modified.add("ViewOnly");
            }
            if (tempData1.contains("SalesExecution_CA")) {
                modified.add("SalesExecution_CA");
            }
            if (tempData1.contains("Scan_CA")) {
                modified.add("Scan_CA");
            }
            if (tempData1.contains("UnrestrictedChain_CA")) {
                modified.add("UnrestrictedChain_CA");
            }
            if (tempData1.contains("RestrictedChain_CA")) {
                modified.add("RestrictedChain_CA");
            }
            if (tempData1.contains("ViewOnly_CA")) {
                modified.add("ViewOnly_CA");
            }
            if (tempData1.contains("SalesLeadModule")) {
                modified.add("SalesLeadModule");
            }
            if (tempData1.contains("SLImpersonation")) {
                modified.add("SLImpersonation");
            }
            if (tempData1.contains("BulkUpload")) {
                modified.add("BulkUpload");
            }

            data1.setModules(modified);
        }



        //////REMOVE Wrong GIPD

        ArrayList<String> wrongWhiteListGipids = new ArrayList<>();


        Iterator<WhiteListData> iterator2 = whiteDataList.iterator();
        while (iterator2.hasNext()) {
            WhiteListData whitedt = iterator2.next();
            if (!whitedt.getGpid().matches("\\d{8}")) {
                wrongWhiteListGipids.add(whitedt.getGpid());
                iterator2.remove();
            }
        }

        ////////

        /// Remove Duplicate from WhiteList///////////////////////////////////

        ArrayList<String> gpidwhitelist = new ArrayList<>();
        ArrayList<String> duplicateGpidwhitelist = new ArrayList<>();

        Iterator<WhiteListData> iterator3 = whiteDataList.iterator();
        while (iterator3.hasNext()) {
            WhiteListData whitedt = iterator3.next();
            if (gpidwhitelist.contains(whitedt.getGpid())) {
                duplicateGpidwhitelist.add(whitedt.getGpid());
                iterator3.remove();
            } else {
                gpidwhitelist.add(whitedt.getGpid());
            }
        }

        /////

        return "WhiteListData has been rearranged.";

    }

    @PutMapping("/modifyScanData")
    public String modifyScanData(){

        // First block: Populate the data map
        HashMap<String, List<String>> data = new HashMap<>();
        for (ScanData dl : scanDataList) {
            data.computeIfAbsent(dl.getGpid(), k -> new ArrayList<>()).addAll(dl.getScanData());
        }

        // Second block: Modify the modules based on conditions
        for (ScanData data1 : scanDataList) {
            List<String> tempData1 = data.get(data1.getGpid());
            List<String> modified = new ArrayList<>();

            if (tempData1.contains("1000027190")) {
                modified.add("1000027190");
            }
            if (tempData1.contains("1000019036")) {
                modified.add("1000019036");
            }
            if (tempData1.contains("1000036962")) {
                modified.add("1000036962");
            }
            if (tempData1.contains("1000037498")) {
                modified.add("1000037498");
            }
            if (tempData1.contains("1000018265")) {
                modified.add("1000018265");
            }
            if (tempData1.contains("1000042333")) {
                modified.add("1000042333");
            }
            if (tempData1.contains("1000027118")) {
                modified.add("1000027118");
            }
            if (tempData1.contains("1000041253")) {
                modified.add("1000041253");
            }
            if (tempData1.contains("1000041227")) {
                modified.add("1000041227");
            }
            if (tempData1.contains("1000027162")) {
                modified.add("1000027162");
            }
            if (tempData1.contains("1000029709")) {
                modified.add("1000029709");
            }
            if (tempData1.contains("1000019208")) {
                modified.add("1000019208");
            }
            if (tempData1.contains("1000032503")) {
                modified.add("1000032503");
            }
            if (tempData1.contains("1000018803")) {
                modified.add("1000018803");
            }
            if (tempData1.contains("1000038132")) {
                modified.add("1000038132");
            }
            if (tempData1.contains("1000033122")) {
                modified.add("1000033122");
            }
            if (tempData1.contains("1000019139")) {
                modified.add("1000019139");
            }
            if (tempData1.contains("1000030772")) {
                modified.add("1000030772");
            }
            if (tempData1.contains("1000041303")) {
                modified.add("1000041303");
            }
            if (tempData1.contains("1000042303")) {
                modified.add("1000042303");
            }
            if (tempData1.contains("1000018990")) {
                modified.add("1000018990");
            }
            data1.setScanData(modified);
        }


        /// Remove Duplicate from Scan///////////////////////////////////

        ArrayList<String> gpidscan = new ArrayList<>();
        ArrayList<String> duplicateGpidscan = new ArrayList<>();

        Iterator<ScanData> iterator = scanDataList.iterator();
        while (iterator.hasNext()) {
            ScanData scandt = iterator.next();
            if (gpidscan.contains(scandt.getGpid())) {
                duplicateGpidscan.add(scandt.getGpid());
                iterator.remove();
            } else {
                gpidscan.add(scandt.getGpid());
            }

        }

        ////Remove Wrong GPID from Scan

        ArrayList<String> wrongScanGipids = new ArrayList<>();
        Iterator<ScanData> iterator3 = scanDataList.iterator();
        while (iterator3.hasNext()) {
            ScanData scndt = iterator3.next();
            if (!scndt.getGpid().matches("\\d{8}")) {
                wrongScanGipids.add(scndt.getGpid());
                iterator3.remove();
            }
        }

        return "ScanData has been rearranged.";
}

}


