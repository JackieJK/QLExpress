package com.alibaba.qlexpress4.runtime.operator.logic;

import com.alibaba.qlexpress4.exception.ErrorReporter;
import com.alibaba.qlexpress4.runtime.Value;
import com.alibaba.qlexpress4.runtime.operator.base.BaseUnaryOperator;
import com.alibaba.qlexpress4.runtime.operator.constant.OperatorPriority;

/**
 * @author 冰够
 */
public class LogicNotOperator extends BaseUnaryOperator {
    @Override
    public Object execute(Value value, ErrorReporter errorReporter) {
        Object operand = value.get();
        if (operand == null) {
            operand = false;
        }
        if (!(operand instanceof Boolean)) {
            throw buildInvalidOperandTypeException(value, errorReporter);
        }

        return !(Boolean)operand;
    }

    @Override
    public String getOperator() {
        return "!";
    }

    @Override
    public int getPriority() {
        return OperatorPriority.PRIORITY_13;
    }
}