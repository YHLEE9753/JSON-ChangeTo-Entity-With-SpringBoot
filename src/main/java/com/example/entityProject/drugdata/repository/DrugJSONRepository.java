package com.example.entityProject.drugdata.repository;

import com.example.entityProject.drugdata.entity.*;
import com.example.entityProject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

@Repository
@RequiredArgsConstructor
public class DrugJSONRepository {
    private final EntityManager em;


    public MyDrug saveJson(User user){
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
                Long visitcnt = Long.parseLong((String)oneCase.get("VISITCNT"));
                String treatdsnm = (String) oneCase.get("TREATDSNM");
                Long treatdate = Long.parseLong((String)oneCase.get("TREATDATE"));
                Long medicinecnt = Long.parseLong((String)oneCase.get("MEDICINECNT"));
                String treatdsgb = (String) oneCase.get("TREATDSGB");
                Long prescribecnt = Long.parseLong((String)oneCase.get("PRESCRIBECNT"));
                String treatmedicalnm = (String) oneCase.get("TREATMEDICALNM");

                MyDrug myDrug = MyDrug.builder()
                        .treatType(caseType)
                        .visitCnt(visitcnt)
                        .treatDsnm(treatdsnm)
                        .treatDate(treatdate)
                        .medicineCnt(medicinecnt)
                        .treatdsgb(treatdsgb)
                        .prescribeCnt(prescribecnt)
                        .treatMedicalnm(treatmedicalnm)
                        .build();
                myDrug.changeUser(user);
                em.persist(myDrug);

                // MyDrug - DrugDetail 일대 다 투약 성분(타입 3번의 경우에 해당)
                JSONArray detailArr = (JSONArray) (oneCase.get("DETAILLIST"));

                Iterator<Object> detailIter = detailArr.iterator();
                while (detailIter.hasNext()) {
                    JSONObject oneDrug = (JSONObject) detailIter.next();

                    // Detail - 투약정보 type2
                    String treattype = (String) oneDrug.get("TREATTYPE");
                    String medicinenm = (String) oneDrug.get("MEDICINENM");
                    Long treatdate1 = Long.parseLong((String)oneDrug.get("TREATDATE"));
                    String medicineeffect = (String) oneDrug.get("MEDICINEEFFECT");
                    String prescribecnt1 = (String) oneDrug.get("PRESCRIBECNT");
                    String administercnt = (String) oneDrug.get("ADMINISTERCNT");

                    MyDrugDetail myDrugDetail = MyDrugDetail.builder()
                            .treatType(treattype)
                            .medicineNm(medicinenm)
                            .treatDate(treatdate1)
                            .medicineEffect(medicineeffect)
                            .prescribeCnt(prescribecnt1)
                            .administerCnt(administercnt)
                            .build();
                    myDrugDetail.changeMyDrug(myDrug);
                    em.persist(myDrugDetail);

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
                        String agetaboo = "";
                        String pregnanttaboo = "";
                        String combinedtaboo = "";


                        // DUR 정보
                        JSONArray durArr = (JSONArray) oneInfo.get("DUR");
                        Iterator<Object> durIter = durArr.iterator();
                        while (durIter.hasNext()) {
                            JSONObject durInfo = (JSONObject) durIter.next();
                            agetaboo = (String) durInfo.get("AGETABOO");
                            pregnanttaboo = (String) durInfo.get("PREGNANTTABOO");
                            combinedtaboo = (String) durInfo.get("COMBINEDTABOO");
                        }


                        MyDrugInfo myDrugInfo = MyDrugInfo.builder()
                                .durAgeTaboo(agetaboo)
                                .pregnantTaboo(pregnanttaboo)
                                .combinedTaboo(combinedtaboo)
                                .makingCompany(makingcompany)
                                .productNm(productnm)
                                .medicineGroup(medicinegroup)
                                .salesCompany(salescompany)
                                .payInfo(payinfo)
                                .administerPath(administerpath)
                                .shape(shape)
                                .singleYn(singleyn)
                                .specialYn(specialyn)
                                .build();
                        myDrugInfo.changeMyDrugDetail(myDrugDetail);
                        em.persist(myDrugInfo);


                        // KPI 정보
                        JSONArray kpiArr = (JSONArray) oneInfo.get("KPICLIST");
                        Iterator<Object> kpiIter = kpiArr.iterator();
                        while (kpiIter.hasNext()) {
                            JSONObject kpiInfo = (JSONObject) kpiIter.next();
                            String kpic = (String) kpiInfo.get("KPIC");

                            KPICList kpicList = KPICList.builder()
                                    .kpic(kpic)
                                    .build();
                            kpicList.changeMyDrugInfo(myDrugInfo);
                            em.persist(kpicList);
                        }

                        // Ingredient 정보
                        JSONArray ingredientnmlist = (JSONArray) oneInfo.get("INGREDIENTNMLIST");
                        Iterator<Object> ingredientIter = ingredientnmlist.iterator();
                        while (ingredientIter.hasNext()) {
                            JSONObject ingredientInfo = (JSONObject) ingredientIter.next();
                            String ingredientnm = (String) ingredientInfo.get("INGREDIENTNM");

                            IngredientList ingredientList = IngredientList.builder()
                                    .ingredientName(ingredientnm)
                                    .build();
                            ingredientList.changeMyDrugInfo(myDrugInfo);
                            em.persist(ingredientList);

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
