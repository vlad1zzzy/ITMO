package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.exceptions.NumberFormat;

public class DoubleOperation implements Operation<Double> {

    public Double parseNum(String num) throws ExpressionException {
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException e) {
            throw new NumberFormat("Wrong number format", 0);
        }
    }

    @Override
    public Double add(Double x, Double y) throws ExpressionException {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) throws ExpressionException {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) throws ExpressionException {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) throws ExpressionException {
        return x / y;
    }

    @Override
    public Double not(Double x) throws ExpressionException {
        return -x;
    }

    @Override
    public Double min(Double x, Double y) throws ExpressionException {
        return (x > y) ? y : x;
    }

    @Override
    public Double max(Double x, Double y) throws ExpressionException {
        return (x > y) ? x : y;
    }

}
