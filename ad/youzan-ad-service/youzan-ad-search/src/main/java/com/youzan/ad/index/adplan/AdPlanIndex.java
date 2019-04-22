package com.youzan.ad.index.adplan;

import com.youzan.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class AdPlanIndex implements IndexAware<Long,AdPlanObject> {

    private static Map<Long,AdPlanObject> objectMap;

    static {
        objectMap=new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("添加之前",objectMap);
        objectMap.put(key,value);
        log.info("添加之后",objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {

        log.info("修改之前",objectMap);
        AdPlanObject planObject=objectMap.get(key);
        if (null==planObject){
            objectMap.put(key, value);
        }else {
            planObject.update(value);
        }
        log.info("修改之后",objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {

        log.info("删除之前",objectMap);
        objectMap.remove(key);
        log.info("删除之后",objectMap);
    }
}
