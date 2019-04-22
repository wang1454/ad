package com.youzan.ad.service;

import com.youzan.ad.exception.AdException;
import com.youzan.ad.vo.CreativeRequest;
import com.youzan.ad.vo.CreativeResopnse;

public interface IAdCreativeService {

    /**
     * 创建 创意 (物料)
     */

    CreativeResopnse creatrCreative(CreativeRequest request) throws AdException;
}
