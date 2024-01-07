package com.alibaba.qlexpress4.runtime.data.checker.convertchecker.array;

import com.alibaba.qlexpress4.runtime.data.checker.TypeConvertChecker;
import com.alibaba.qlexpress4.runtime.data.implicit.QLConvertResult;
import com.alibaba.qlexpress4.runtime.data.implicit.QLConvertResultType;

import java.util.stream.LongStream;

/**
 * Author: TaoKan
 */
public class QLArrayLongStreamToPrimitiveDoubleChecker implements TypeConvertChecker<Object> {

    @Override
    public QLConvertResult typeReturn(Object value, Class<?> type) {
        return new QLConvertResult(QLConvertResultType.CAN_TRANS, ((LongStream) value).asDoubleStream().toArray());
    }
}