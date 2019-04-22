package com.youzan.ad.search.vo;

import com.youzan.ad.search.vo.feature.DistrictFeature;
import com.youzan.ad.search.vo.feature.FeatureRelation;
import com.youzan.ad.search.vo.feature.ItFeature;
import com.youzan.ad.search.vo.feature.KeyWordFeature;
import com.youzan.ad.search.vo.media.Adslot;
import com.youzan.ad.search.vo.media.App;
import com.youzan.ad.search.vo.media.Device;
import com.youzan.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    //媒体方的请求标识
    private String mediaId;
    //请求基本信息
    private RequestInfo requestInfo;
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {

        private String requestId;

        private List<Adslot> adslots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo{

        private KeyWordFeature keyWordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        private FeatureRelation relation=FeatureRelation.AND;
    }

}
