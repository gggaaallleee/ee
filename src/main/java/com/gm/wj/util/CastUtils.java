package com.gm.wj.util;

import java.util.ArrayList;
import java.util.List;

//负责类型转换，将Object类型转换为List<T>类型，原因是redisTemplate.opsForValue().get(key)返回的是Object类型
public class CastUtils {
    public static <T> List<T> objectConvertToList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
