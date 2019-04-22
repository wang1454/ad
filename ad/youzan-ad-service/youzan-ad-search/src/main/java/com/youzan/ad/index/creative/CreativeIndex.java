package com.youzan.ad.index.creative;

import com.youzan.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long,CreativeObject> {

    private static Map<Long,CreativeObject> objectMap;

    static {
        objectMap=new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> adIds) {
        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }
        List<CreativeObject>  result=new ArrayList<>();
            adIds.forEach(
                    u->{
                        CreativeObject object=get(u);
                        if (null==object) {
                            log.error("CreativeObject not found :{}",u);
                            return;
                        }
                        result.add(object);
                    });
            return result;
        }


    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, CreativeObject value) {

        CreativeObject creativeObject = objectMap.get(key);
        if (null==creativeObject){
            objectMap.put(key, value);
        }else {
            creativeObject.update(value);
        }
    }

    @Override
    public void delete(Long key, CreativeObject value) {
          objectMap.remove(key);
    }
}
