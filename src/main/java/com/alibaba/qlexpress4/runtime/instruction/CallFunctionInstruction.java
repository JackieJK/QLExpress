package com.alibaba.qlexpress4.runtime.instruction;

import com.alibaba.qlexpress4.QLOptions;
import com.alibaba.qlexpress4.exception.ErrorReporter;
import com.alibaba.qlexpress4.exception.UserDefineException;
import com.alibaba.qlexpress4.runtime.*;
import com.alibaba.qlexpress4.runtime.data.DataValue;
import com.alibaba.qlexpress4.runtime.util.ThrowUtils;
import com.alibaba.qlexpress4.runtime.util.ValueUtils;
import com.alibaba.qlexpress4.utils.PrintlnUtils;

import java.util.function.Consumer;

/**
 * @Operation: call ql function from function table
 * @Input: ${argNum}
 * @Output: 1 function result
 * Author: DQinYuan
 */
public class CallFunctionInstruction extends QLInstruction {

    private final String functionName;

    private final int argNum;

    public CallFunctionInstruction(ErrorReporter errorReporter, String functionName, int argNum) {
        super(errorReporter);
        this.functionName = functionName;
        this.argNum = argNum;
    }

    @Override
    public QResult execute(QRuntime qRuntime, QLOptions qlOptions) {
        QFunction function = qRuntime.getFunction(functionName);
        if (function == null) {
            callLambda(qRuntime);
            return QResult.CONTINUE_RESULT;
        }
        Parameters parameters = qRuntime.pop(argNum);
        try {
            qRuntime.push(new DataValue(function.call(qRuntime, parameters)));
            return QResult.CONTINUE_RESULT;
        } catch (UserDefineException e) {
            throw errorReporter.report("CALL_FUNCTION_BIZ_EXCEPTION", e.getMessage());
        } catch (Exception e) {
            throw ThrowUtils.wrapException(e, errorReporter, "CALL_FUNCTION_UNKNOWN_EXCEPTION",
                    "call function unknown exception");
        }
    }

    private void callLambda(QRuntime qRuntime) {
        Object lambdaSymbol = qRuntime.getSymbolValue(functionName);
        if (!(lambdaSymbol instanceof QLambda)) {
            throw errorReporter.report("CAN_NOT_FIND_FUNCTION", "can not find function %s",
                    functionName);
        }
        Parameters parameters = qRuntime.pop(argNum);
        Object[] parametersArr = new Object[parameters.size()];
        for (int i = 0; i < parametersArr.length; i++) {
            parametersArr[i] = parameters.get(i);
        }
        try {
            Value resultValue = ((QLambda) lambdaSymbol).call(parametersArr).getResult();
            qRuntime.push(ValueUtils.toImmutable(resultValue));
        } catch (Exception e) {
            throw ThrowUtils.wrapException(e, errorReporter,
                    "LAMBDA_EXECUTE_EXCEPTION", "lambda execute exception");
        }
    }

    @Override
    public int stackInput() {
        return argNum;
    }

    @Override
    public int stackOutput() {
        return 1;
    }

    @Override
    public void println(int depth, Consumer<String> debug) {
        PrintlnUtils.printlnByCurDepth(depth, "CallFunction " + functionName + " " + argNum, debug);
    }
}
