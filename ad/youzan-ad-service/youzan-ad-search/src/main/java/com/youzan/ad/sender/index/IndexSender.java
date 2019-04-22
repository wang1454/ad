package com.youzan.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.youzan.ad.dump.table.*;
import com.youzan.ad.handler.AdLevelDataHandler;
import com.youzan.ad.index.DateLevel;
import com.youzan.ad.mysql.constant.Constant;
import com.youzan.ad.mysql.dto.MySqlRowData;
import com.youzan.ad.sender.ISender;
import com.youzan.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {

        String level = rowData.getLevel();
        if (DateLevel.LEVEL2.getLevel().equals(level)) {
            Level2RowData(rowData);
        } else if (DateLevel.LEVEL3.getLevel().equals(level)) {
            Level3RowData(rowData);
        } else if (DateLevel.LEVEL4.getLevel().equals(level)) {
            Level4RowData(rowData);
        } else {
            log.error("MysqlRowDate ERROR :{}", JSON.toJSONString(rowData));
        }
    }

    private void Level2RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> planTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLIMN_ID:
                            planTable.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLIMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLIMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLIMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLIMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                planTables.add(planTable);
            }

            planTables.forEach(
                    p -> AdLevelDataHandler.handleLevel2(p, rowData.getOpType())
            );
        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME
        )) {
            List<AdCreativeTbale> creativeTbales = new ArrayList<>();

            for (Map<String, String> fieldValeuMap : rowData.getFieldValueMap()) {
                AdCreativeTbale creativeTbale = new AdCreativeTbale();
                fieldValeuMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTbale.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTbale.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTbale.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTbale.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTbale.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTbale.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTbale.setAdUrl(v);
                            break;
                    }
                });
                creativeTbales.add(creativeTbale);
            }
            creativeTbales.forEach(
                    c -> AdLevelDataHandler.handleLevel2(c, rowData.getOpType())
            );
        }
    }

    private void Level3RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AD_UNIT_TABLE_INFO.TABLE_NAME
        )) {
            List<AdUnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {
                AdUnitTable unitTable = new AdUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNTI_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u ->
                    AdLevelDataHandler.handlelevel3(u, rowData.getOpType()));
        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME
        )) {
            List<AdCreativeUnitTbale> creativeUnitTbales = new ArrayList<>();

            for (Map<String, String> fieldValueMap :
                    rowData.getFieldValueMap()) {
                AdCreativeUnitTbale creativeUnitTbale = new AdCreativeUnitTbale();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTbale.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTbale.setUnitId(Long.valueOf(v));
                            break;
                    }
                    creativeUnitTbales.add(creativeUnitTbale);
                });
                creativeUnitTbales.forEach(
                        u -> AdLevelDataHandler.handlevel3(u, rowData.getOpType())
                );
            }
        }
    }

    private void Level4RowData(MySqlRowData rowData) {
        switch (rowData.getTableName()) {
            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                List<AdUnitDistrictTable> districtTables = new ArrayList<>();

                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {
                    AdUnitDistrictTable districtTable = new AdUnitDistrictTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                districtTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                districtTable.setProvince(v);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                districtTable.setCity(v);
                                break;
                        }
                    });
                    districtTables.add(districtTable);
                }
                districtTables.forEach(
                        d -> AdLevelDataHandler.hanfleLevel4(d, rowData.getOpType())
                );
                break;
            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                List<AdUnitItTable> itTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {
                    AdUnitItTable itTable = new AdUnitItTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                itTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_TAG:
                                itTable.setItTag(v);
                                break;
                        }
                    });
                    itTables.add(itTable);
                }
                itTables.forEach(
                        i -> AdLevelDataHandler.handleLevel4(i, rowData.getOpType())
                );
                break;
            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                List<AdUnitKeywordTable> keywordTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap :
                        rowData.getFieldValueMap()) {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                        }
                        keywordTables.add(keywordTable);
                    });
                    keywordTables.add(keywordTable);
                }
                keywordTables.forEach(
                        k->AdLevelDataHandler.handLevel4(k,rowData.getOpType())
                );
                break;
        }
    }
}
