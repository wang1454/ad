package com.youzan.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;

    private Integer positionType;

    private Long budget;

    public boolean createvalidate(){
        return null!=planId&&!StringUtils.isEmpty((unitName))
                &&null!=positionType&&null!=budget;
    }
}
