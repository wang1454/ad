package com.youzan.ad.index.keyword;

import com.youzan.ad.index.IndexAware;
import com.youzan.ad.search.vo.feature.DistrictFeature;
import com.youzan.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@Slf4j
public class UnitKeyWordIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> keyWordUnitMap;
    private static Map<Long, Set<String>> unitKeyWordMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        keyWordUnitMap = new ConcurrentHashMap<>();
        unitKeyWordMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {

        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }
        Set<Long> set = keyWordUnitMap.get(key);
        if (null == set) {
            return Collections.emptySet();
        }
        return set;
    }

    @Override
    public void add(String key, Set<Long> value) {

        Set<Long> set = CommonUtils.getorCreate(key, keyWordUnitMap,
                ConcurrentSkipListSet::new);

        set.addAll(value);

        for (Long unitId : value
        ) {
            Set<String> keyWoedSet = CommonUtils.getorCreate(unitId, unitKeyWordMap,
                    ConcurrentSkipListSet::new);
            keyWoedSet.add(key);
        }

    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("没有可用的更新");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        Set<Long> set = CommonUtils.getorCreate(key, keyWordUnitMap,
                ConcurrentSkipListSet::new
        );

        set.removeAll(value);

        for (Long unitId : value
        ) {
            Set<String> keyWordSet = CommonUtils.getorCreate(unitId, unitKeyWordMap,
                    ConcurrentSkipListSet::new
            );
            keyWordSet.remove(key);
        }
    }

    public boolean match(Long unitId, List<String> keywords) {
        if (unitKeyWordMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitKeyWordMap.get(unitId))) {
            Set<String> unitKeywords = unitDistrictMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords,unitKeywords);
        }
        return false;
    }
}
