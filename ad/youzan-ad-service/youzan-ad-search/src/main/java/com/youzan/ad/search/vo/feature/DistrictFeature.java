package com.youzan.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictFeature {

    private List<ProvinceandCity> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static  class ProvinceandCity{
         private String province;
         private String city;
    }
}
