package com.youzan.ad.service.impl;

import com.youzan.ad.dao.AdCreativeRepository;
import com.youzan.ad.entity.AdCreative;
import com.youzan.ad.exception.AdException;
import com.youzan.ad.service.IAdCreativeService;
import com.youzan.ad.vo.CreativeRequest;
import com.youzan.ad.vo.CreativeResopnse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdCreativeServiceImpl implements IAdCreativeService {

    @Autowired
    AdCreativeRepository creativeRepository;

    @Override
    @Transactional
    public CreativeResopnse creatrCreative(CreativeRequest request) throws AdException{

        AdCreative creative=creativeRepository.save(request.toEntity());

        return new CreativeResopnse(creative.getId(),creative.getName());
    }
}
