package com.youzan.ad.index.adunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    public void update(AdUnitObject newObjct) {
        if (null != newObjct.getUnitId()) {
            this.unitId = newObjct.getUnitId();
        }
        if (null != newObjct.getUnitStatus()) {
            this.unitStatus = newObjct.getUnitStatus();
        }
        if (null != newObjct.getPositionType()) {
            this.positionType = newObjct.positionType;
        }
        if (null != newObjct.getPlanId()) {
            this.planId = newObjct.getPlanId();
        }
    }

    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstants.POSITON_TYPE.KAIPING) > 0;
    }

    private static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstants.POSITON_TYPE.TIEPIAN) > 0;
    }

    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstants.POSITON_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstants.POSITON_TYPE.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstants.POSITON_TYPE.TIEPIAN_POST) > 0;
    }

    public static boolean isAdSlotTypeOK(int adSlotType, int positionType) {
        switch (adSlotType) {
            case AdUnitConstants.POSITON_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.POSITON_TYPE.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstants.POSITON_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstants.POSITON_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstants.POSITON_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }
}
