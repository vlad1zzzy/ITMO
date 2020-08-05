package expression.generic.operations;

import expression.exceptions.DivideByZero;
import expression.exceptions.ExpressionException;
import expression.exceptions.NumberFormat;
import expression.exceptions.Overflow;

public class LongOperation implements Operation<Long> {

    public Long parseNum(String num) throws ExpressionException {
        try {
            return Long.parseLong(num);
        } catch (NumberFormatException e) {
            throw new NumberFormat("Wrong number format", 0);
        }
    }

    @Override
    public Long add(Long x, Long y) throws ExpressionException {
        return x + y;
    }

    @Override
    public Long sub(Long x, Long y) throws ExpressionException {
        return x - y;
    }

    @Override
    public Long mul(Long x, Long y) throws ExpressionException {
        return x * y;
    }

    @Override
    public Long div(Long x, Long y) throws ExpressionException {
        return x / y;
    }

    @Override
    public Long not(Long x) throws ExpressionException {
        return -x;
    }


    @Override
    public Long min(Long x, Long y) throws ExpressionException {
        return (x > y) ? y : x;
    }

    @Override
    public Long max(Long x, Long y) throws ExpressionException {
        return (x > y) ? x : y;
    }

    private Long abs(Long x) {
        return x >= 0 ? x : -x;
    }
}
