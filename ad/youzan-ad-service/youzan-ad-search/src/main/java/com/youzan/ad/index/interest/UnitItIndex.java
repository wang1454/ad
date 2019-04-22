package com.youzan.ad.index.interest;

import com.youzan.ad.index.IndexAware;
import com.youzan.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> itTagUnitMap;
    private static Map<Long, Set<String>> unitItTagMap;

    static {
        itTagUnitMap = new ConcurrentHashMap<>();
        unitItTagMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itTagUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        CommonUtils.getorCreate(
                key,
                itTagUnitMap,
                ConcurrentSkipListSet::new
        ).addAll(value);

        value.forEach(
                i->CommonUtils.getorCreate(
                        i,
                        unitItTagMap,
                        ConcurrentSkipListSet::new
                ).add(key)
        );
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("没有可供的更新");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        CommonUtils.getorCreate(
                key,
                itTagUnitMap,
                ConcurrentSkipListSet::new
        ).removeAll(value);

        value.forEach(
                i->CommonUtils.getorCreate(
                        i,
                        unitItTagMap,
                        ConcurrentSkipListSet::new
                ).remove(key)
        );
    }

    public boolean match(Long unitId, List<String> its) {
        if (unitItTagMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItTagMap.get(unitId))) {
            Set<String> unitIts = unitItTagMap.get(unitId);
            return CollectionUtils.isSubCollection(its,unitIts);
        }
        return false;
    }
}
