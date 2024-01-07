package com.alibaba.qlexpress4.runtime.data.checker.convertchecker;

import com.alibaba.qlexpress4.runtime.data.checker.TypeConvertChecker;
import com.alibaba.qlexpress4.runtime.data.convert.ArrayConversion;
import com.alibaba.qlexpress4.runtime.data.implicit.QLConvertResult;

/**
 * Author: TaoKan
 */
public class QLArrayConvertChecker implements TypeConvertChecker<Object> {

    @Override
    public boolean typeCheck(Object value, Class<?> type) {
        return type.isArray();
    }

    @Override
    public QLConvertResult typeReturn(Object value, Class<?> type) {
        return ArrayConversion.trans(value, type);
    }
}