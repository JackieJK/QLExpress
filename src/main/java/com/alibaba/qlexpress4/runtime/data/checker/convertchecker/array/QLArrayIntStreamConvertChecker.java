package com.alibaba.qlexpress4.runtime.data.checker.convertchecker.array;

import com.alibaba.qlexpress4.runtime.data.checker.TypeConvertChecker;
import com.alibaba.qlexpress4.runtime.data.implicit.QLConvertResult;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Author: TaoKan
 */
public class QLArrayIntStreamConvertChecker implements TypeConvertChecker<Object> {

    private final Map<Class<?>,TypeConvertChecker> intSteamMap;

    public QLArrayIntStreamConvertChecker(){
        intSteamMap = new HashMap<>(4);
        intSteamMap.put(int[].class,new QLArrayIntStreamToPrimitiveChecker());
        intSteamMap.put(long[].class,new QLArrayIntStreamToPrimitiveLongChecker());
        intSteamMap.put(double[].class,new QLArrayIntStreamToPrimitiveDoubleChecker());
        intSteamMap.put(Integer[].class,new QLArrayIntStreamToIntChecker());
    }

    @Override
    public boolean typeCheck(Object value, Class<?> type) {
        return value instanceof IntStream;
    }

    @Override
    public QLConvertResult typeReturn(Object value, Class<?> type) {
        TypeConvertChecker checker = intSteamMap.get(type);
        return checker == null?null:(QLConvertResult) checker.typeReturn(value,type);
    }
}