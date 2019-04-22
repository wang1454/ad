package com.youzan.ad.index.district;

import com.youzan.ad.index.IndexAware;
import com.youzan.ad.search.vo.feature.DistrictFeature;
import com.youzan.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    //<地域（省——市）,set unitId>
    private static Map<String, Set<Long>> districtUnitMap;

    //<nuitId,Set 地域（省——市）>
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        value.forEach(
                i -> CommonUtils.getorCreate(
                        i,
                        unitDistrictMap,
                        ConcurrentSkipListSet::new
                ).add(key)
        );
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("没有可提供的更新");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                districtUnitMap, ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        value.forEach(
                i -> CommonUtils.getorCreate(
                        i,
                        unitDistrictMap,
                        CopyOnWriteArraySet::new
                ).remove(key)
        );
        log.info("UnitDistrictIndex,after delete:{}", unitDistrictMap);
    }

    public boolean match(Long adUnitId,
                         List<DistrictFeature.ProvinceandCity> districts) {
        if (unitDistrictMap.containsKey(adUnitId) &&
                CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitId))) {
            Set<String> unitDistricts= unitDistrictMap.get(adUnitId);

            List<String> targetDistricts = districts.stream()
                    .map(
                            d -> CommonUtils.stringConCat(
                                    d.getProvince(),
                                    d.getCity()
                            )
                    ).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts,unitDistricts);
        }
        return false;
    }
}
