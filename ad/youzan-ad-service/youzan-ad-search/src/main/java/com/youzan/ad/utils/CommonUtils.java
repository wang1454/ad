package com.youzan.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by baimugudu on 2019/4/2
 */
@Slf4j
public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> supplier) {
        return map.computeIfAbsent(key,
                k -> supplier.get());
    }

    public static String stringConCat(String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args
        ) {
            stringBuilder.append(arg);
            stringBuilder.append("-");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    public static Date parseStringDate(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8
            );


        } catch (ParseException e) {
            log.error("parseStringDate error :{}", dateString);
            return null;
        }
    }
}
