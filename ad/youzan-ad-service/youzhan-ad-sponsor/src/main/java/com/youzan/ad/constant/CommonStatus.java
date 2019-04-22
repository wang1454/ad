package com.youzan.ad.constant;

import lombok.Getter;

@Getter
public enum CommonStatus {

    VALID(1,"有效状态"),
    INVALID(0,"无效状态");

    Integer status;
    String desc;

    CommonStatus(Integer status, String desc){
        this.desc=desc;
        this.status=status;
    }
}
