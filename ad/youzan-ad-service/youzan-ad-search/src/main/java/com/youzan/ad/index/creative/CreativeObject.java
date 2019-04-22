package com.youzan.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {

    private Long adId;
    private String adUrl;
    private String name;
    private Integer type;
    private Integer meterialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;

    public void update(CreativeObject newObject){
        if (null!=newObject.getAdId()){
            this.adId=newObject.getAdId();
        }
        if (null!=newObject.getAdUrl()){
            this.adUrl=newObject.getAdUrl();
        }
        if (null!=newObject.getName()){
            this.name=newObject.getName();
        }
        if (null!=newObject.getType()){
            this.type=newObject.getType();
        }
        if (null!=newObject.getMeterialType()){
            this.meterialType=newObject.getMeterialType();
        }
        if (null!=newObject.getHeight()){
            this.height=newObject.getHeight();
        }
        if (null!=newObject.getWidth()){
            this.width=newObject.getWidth();
        }
        if (null!=newObject.getAuditStatus()){
            this.auditStatus=newObject.getAuditStatus();
        }
    }
}
