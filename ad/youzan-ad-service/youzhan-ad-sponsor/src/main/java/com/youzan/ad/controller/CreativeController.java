package com.youzan.ad.controller;

import com.alibaba.fastjson.JSON;
import com.youzan.ad.exception.AdException;
import com.youzan.ad.service.IAdCreativeService;
import com.youzan.ad.vo.CreativeRequest;
import com.youzan.ad.vo.CreativeResopnse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/creative")
public class CreativeController {

    @Autowired
    IAdCreativeService creativeService;

    public CreativeResopnse creativeResopnse(@RequestBody CreativeRequest request)throws AdException{
        log.info("youzan-ad-sponsor:creativeResponse-{}", JSON.toJSONString(request));

        return creativeService.creatrCreative(request);
    }
}
