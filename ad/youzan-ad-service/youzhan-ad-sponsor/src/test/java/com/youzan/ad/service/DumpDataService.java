package com.youzan.ad.service;

import com.alibaba.fastjson.JSON;
import com.youzan.ad.Application;
import com.youzan.ad.constant.CommonStatus;
import com.youzan.ad.dao.AdCreativeRepository;
import com.youzan.ad.dao.AdPlanRepository;
import com.youzan.ad.dao.AdUnitRepository;
import com.youzan.ad.dao.unit_condition.AdCreativeUnitRepository;
import com.youzan.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.youzan.ad.dao.unit_condition.AdUnitItRepository;
import com.youzan.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.youzan.ad.dump.table.*;
import com.youzan.ad.entity.AdCreative;
import com.youzan.ad.entity.AdPlan;
import com.youzan.ad.entity.AdUnit;
import com.youzan.ad.entity.unit_condition.AdCreativeUnit;
import com.youzan.ad.entity.unit_condition.AdUnitDistrict;
import com.youzan.ad.entity.unit_condition.AdUnitIt;
import com.youzan.ad.entity.unit_condition.AdUnitKeyword;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private AdCreativeRepository adCreativeRepository;

    @Autowired
    private AdCreativeUnitRepository adCreativeUnitRepository;

    @Autowired
    private AdUnitItRepository adUnitItRepository;

    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;

    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;

    private void dumpAdplanTable(String fileName) {
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );

        List<AdPlanTable> adPlanTables = new ArrayList<>();

        adPlans.forEach(
                i -> adPlanTables.add(
                        new AdPlanTable(
                                i.getId(),
                                i.getUserId(),
                                i.getPlanStatus(),
                                i.getStartTime(),
                                i.getEndTime()
                        )
                )
        );

        final Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (AdPlanTable adPlanTable : adPlanTables) {
                bufferedWriter.write(JSON.toJSONString(adPlanTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


    private void dumpUnitTable(String fileName) {
        List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus()
        );

        List<AdUnitTable> adUnitTables = new ArrayList<>();

        adUnits.forEach(
                i -> adUnitTables.add(
                        new AdUnitTable(
                                i.getId(),
                                i.getUnitStatus(),
                                i.getPositionType(),
                                i.getPlanId()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
           for (AdUnitTable adUnitTable:adUnitTables){
               writer.write(JSON.toJSONString(adUnitTable));
               writer.newLine();
           }
           writer.close();
        }catch (IOException e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void  dumpCreativeTable(String fileName){
        List<AdCreative> creativeList=adCreativeRepository.findAll();
        List<AdCreativeTbale> adCreativeTbales=new ArrayList<>();

        creativeList.forEach(
                c->adCreativeTbales.add(
                        new AdCreativeTbale(
                                c.getId(),
                                c.getName(),
                                c.getType(),
                                c.getMaterialType(),
                                c.getHeight(),
                                c.getWidth(),
                                c.getAuditStatus()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try (BufferedWriter writer=Files.newBufferedWriter(path)){
            for (AdCreativeTbale adCreativeTbale:adCreativeTbales){
                writer.write(JSON.toJSONString(adCreativeTbale));
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void dumpCreativeUnitTable(String fileName){
        List<AdCreativeUnit> adCreativeUnits=adCreativeUnitRepository.findAll();
        List<AdCreativeUnitTbale> adCreativeUnitTbales=new ArrayList<>();
        adCreativeUnits.forEach(
                ad->adCreativeUnitTbales.add(
                        new AdCreativeUnitTbale(
                                ad.getUnitId(),
                                ad.getCreativeId()
                        )
                )
        );

        Path path = Paths.get(fileName);

        try(BufferedWriter writer=Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTbale adCreativeTbale:adCreativeUnitTbales){
                writer.write(JSON.toJSONString(adCreativeTbale));
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dumpCreativeDistrictTable(String fileName){

        List<AdUnitDistrict> adUnitDistricts=adUnitDistrictRepository.findAll();
        List<AdUnitDistrictTable> adUnitDistrictTables=new ArrayList<>();

        adUnitDistricts.forEach(
                i->adUnitDistrictTables.add(
                        new AdUnitDistrictTable(
                                i.getUnitId(),
                                i.getProvince(),
                                i.getCity()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try(BufferedWriter writer=Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable adUnitDistrictTable:adUnitDistrictTables
                 ) {
               writer.write(JSON.toJSONString(adUnitDistrictTable));
               writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dumpCreativeItTable(String fileName){
        List<AdUnitIt> adUnitIts=adUnitItRepository.findAll();
        List<AdUnitItTable> adUnitItTables=new ArrayList<>();

        adUnitIts.forEach(
                i->adUnitItTables.add(
                        new AdUnitItTable(
                                i.getUnitId(),
                                i.getItTag()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try (BufferedWriter writer=Files.newBufferedWriter(path)){
            for (AdUnitItTable adUnitItTable:adUnitItTables){
                writer.write(JSON.toJSONString(adUnitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dumpCreativeKeyWord(String fileName){
        List<AdUnitKeyword> adUnitKeywords=adUnitKeywordRepository.findAll();
        List<AdUnitKeywordTable> adUnitKeywordTables=new ArrayList<>();

        adUnitKeywords.forEach(
                i->adUnitKeywordTables.add(
                        new AdUnitKeywordTable(
                                i.getUnitId(),
                                i.getKeyword()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try (BufferedWriter writer=Files.newBufferedWriter(path)){
            for (AdUnitKeywordTable adUnitKeywordTable:adUnitKeywordTables){
                writer.write(JSON.toJSONString(adUnitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

