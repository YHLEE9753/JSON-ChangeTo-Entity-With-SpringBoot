package com.example.entityProject.drugdata.service;


import com.example.entityProject.drugdata.entity.*;
import com.example.entityProject.drugdata.repository.DrugRepository;
import com.example.entityProject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DrugService {
    private final DrugRepository drugRepository;


    // 회원 가입
    @Transactional
    public Long join(MyDrug myDrug){
        drugRepository.save(myDrug);
        return myDrug.getId();
    }



    public void readJson() {
        // simple json 사용
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("D:\\drugInfo.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String errCode = (String) jsonObject.get("errCode");
            String errMsg = (String) jsonObject.get("errMsg");
            String result = (String) jsonObject.get("result");

            JSONObject data = (JSONObject) jsonObject.get("data");
            Object medicinelist = data.get("MEDICINELIST");
            JSONArray medicineArr = (JSONArray) data.get("MEDICINELIST");

            Iterator<Object> drugIter = medicineArr.iterator();
            while (drugIter.hasNext()) {
                JSONObject oneCase = (JSONObject) drugIter.next();
                String caseType = (String) oneCase.get("TREATTYPE");

                // MyDrug - 투약정보 type1
                String visitcnt = (String) oneCase.get("VISITCNT");
                String treatdsnm = (String) oneCase.get("TREATDSNM");
                String treatdate = (String) oneCase.get("TREATDATE");
                String medicinecnt = (String) oneCase.get("MEDICINECNT");
                String treatdsgb = (String) oneCase.get("TREATDSGB");
                String prescribecnt = (String) oneCase.get("PRESCRIBECNT");
                String treatmedicalnm = (String) oneCase.get("TREATMEDICALNM");

                // MyDrug - DrugDetail 일대 다 투약 성분(타입 3번의 경우에 해당)
                JSONArray detailArr = (JSONArray) (oneCase.get("DETAILLIST"));

                Iterator<Object> detailIter = detailArr.iterator();
                while (detailIter.hasNext()) {
                    JSONObject oneDrug = (JSONObject) detailIter.next();

                    // Detail - 투약정보 type2
                    String treattype = (String) oneDrug.get("TREATTYPE");
                    String medicinenm = (String) oneDrug.get("MEDICINENM");
                    String treatdate1 = (String) oneDrug.get("TREATDATE");
                    String medicineeffect = (String) oneDrug.get("MEDICINEEFFECT");
                    String prescribecnt1 = (String) oneDrug.get("PRESCRIBECNT");
                    String administercnt = (String) oneDrug.get("ADMINISTERCNT");

                    // MyDrug - DrugInfo
                    JSONArray infoArr = (JSONArray) (oneDrug.get("DRUGINFOLIST"));
                    Iterator<Object> infoIter = infoArr.iterator();
                    while (infoIter.hasNext()) {
                        JSONObject oneInfo = (JSONObject) infoIter.next();
                        String makingcompany = (String) oneInfo.get("MAKINGCOMPANY");
                        String productnm = (String) oneInfo.get("PRODUCTNM");
                        String medicinegroup = (String) oneInfo.get("MEDICINEGROUP");
                        String salescompany = (String) oneInfo.get("SALESCOMPANY");
                        String payinfo = (String) oneInfo.get("PAYINFO");
                        String atc = (String) oneInfo.get("ATC");
                        String administerpath = (String) oneInfo.get("ADMINISTERPATH");
                        String shape = (String) oneInfo.get("SHAPE");
                        String singleyn = (String) oneInfo.get("SINGLEYN");
                        String specialyn = (String) oneInfo.get("SPECIALYN");

                        // DUR 정보
                        JSONArray durArr = (JSONArray) oneInfo.get("DUR");
                        Iterator<Object> durIter = durArr.iterator();
                        while (durIter.hasNext()) {
                            JSONObject durInfo = (JSONObject) durIter.next();
                            String agetaboo = (String) durInfo.get("AGETABOO");
                            String pregnanttaboo = (String) durInfo.get("PREGNANTTABOO");
                            String combinedtaboo = (String) durInfo.get("COMBINEDTABOO");
                        }

                        // KPI 정보
                        JSONArray kpiArr = (JSONArray) oneInfo.get("KPICLIST");
                        Iterator<Object> kpiIter = kpiArr.iterator();
                        while (kpiIter.hasNext()) {
                            JSONObject kpiInfo = (JSONObject) kpiIter.next();
                            String kpic = (String) kpiInfo.get("KPIC");
                        }

                        // Ingredient 정보
                        JSONArray ingredientnmlist = (JSONArray) oneInfo.get("INGREDIENTNMLIST");
                        Iterator<Object> ingredientIter = ingredientnmlist.iterator();
                        while (ingredientIter.hasNext()) {
                            JSONObject ingredientInfo = (JSONObject) ingredientIter.next();
                            String ingredientnm = (String) ingredientInfo.get("INGREDIENTNM");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
