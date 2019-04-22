package com.youzan.ad.search.impl;

import com.youzan.ad.index.DataTable;
import com.youzan.ad.index.adunit.AdUnitIndex;
import com.youzan.ad.index.adunit.AdUnitObject;
import com.youzan.ad.index.district.UnitDistrictIndex;
import com.youzan.ad.index.interest.UnitItIndex;
import com.youzan.ad.index.keyword.UnitKeyWordIndex;
import com.youzan.ad.search.ISearch;
import com.youzan.ad.search.vo.SearchRequest;
import com.youzan.ad.search.vo.SearchResponse;
import com.youzan.ad.search.vo.feature.DistrictFeature;
import com.youzan.ad.search.vo.feature.FeatureRelation;
import com.youzan.ad.search.vo.feature.ItFeature;
import com.youzan.ad.search.vo.feature.KeyWordFeature;
import com.youzan.ad.search.vo.media.Adslot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SearchImpl implements ISearch {
    /**
     * @param request
     * @return
     */
    @Override
    public SearchResponse fatchAds(SearchRequest request) {
        // 请求的广告位信息
        List<Adslot> adslots = request.getRequestInfo().getAdslots();
        //三个 Feature
        KeyWordFeature keyWordFeature =
                request.getFeatureInfo().getKeyWordFeature();
        DistrictFeature districtFeature =
                request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature =
                request.getFeatureInfo().getItFeature();
        FeatureRelation relation =
                request.getFeatureInfo().getRelation();
        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads =
                response.getAdSlot2Ads();
        for (Adslot adslot : adslots) {
            Set<Long> targetUnitIdSet;
            //根据流量类型获取初始 AdUnit
            Set<Long> adUnitIdSet = DataTable.of(
                    AdUnitIndex.class
            ).match(adslot.getPositionType());
            if (relation == FeatureRelation.AND) {

                filterKeywordFeature(adUnitIdSet, keyWordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet=getORRelationUnitIds(
                        adUnitIdSet,
                        keyWordFeature,
                        districtFeature,
                        itFeature
                );
            }


        }
        return null;
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeyWordFeature keyWordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet= new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet= new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet= new HashSet<>(adUnitIdSet);

        filterKeywordFeature(adUnitIdSet, keyWordFeature);
        filterDistrictFeature(adUnitIdSet, districtFeature);
        filterItTagFeature(adUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet,districtUnitIdSet),
                        itUnitIdSet
                )
        );

    }

    private void filterKeywordFeature(
            Collection<Long> adUnitIds, KeyWordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitKeyWordIndex.class)
                                    .match(adUnitId,
                                            keywordFeature.getKeywords())

            );
        }
    }

    private void filterDistrictFeature(
            Collection<Long> adUnitIds, DistrictFeature districtFeature
    ) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitDistrictIndex.class)
                                    .match(adUnitId,
                                            districtFeature.getDistricts())
            );
        }
    }

    private void filterItTagFeature(Collection<Long> adUnitIds,
                                    ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitItIndex.class)
                                    .match(adUnitId, itFeature.getIts())
            );
        }
    }
}
