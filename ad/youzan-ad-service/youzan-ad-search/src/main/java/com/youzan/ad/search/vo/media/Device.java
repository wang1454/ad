package com.youzan.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    //设备id
    private String deviceCode;
    //mac
    private String mac;
    //ip
    private String ip;
    //机型编码
    private  String model;
    //分辨率尺寸
    private String displaySize;
    //屏幕尺寸
    private String screesSize;
    //设备序列号
    private String serialName;
}
