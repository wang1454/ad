package com.youzan.ad.service;

import com.youzan.ad.exception.AdException;
import com.youzan.ad.vo.*;

public interface IAdUnitService {
    /**
     * 创建推广单元
     */
     AdUnitResponse createUnit(AdUnitRequest unitRequest)throws AdException;

    /**
     * 创建推广单元 关键词的限制
     */
     AdUnitKeywordResponse createUnitKeyWord(AdUnitKeyWordRequest unitKeyWordRequest);

    /**
     * 创建推广单元 兴趣的限制
     */
     AdUnitItResponse creatUnitIt(AdUnitItRequest unitItRequest);

    /**
     * 创建推广单元 地域限制
     */
     AdUnitDistrictResponse creatAdUnitDistrict(AdUnitDistrictRequest unitDistrictRequest);

    /**
     * 单元-创意
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest uitRequest);
}
