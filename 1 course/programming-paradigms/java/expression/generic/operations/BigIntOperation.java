package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.exceptions.NumberFormat;

import java.math.BigInteger;

public class BigIntOperation implements Operation<BigInteger> {

    public BigInteger parseNum(String num) throws ExpressionException {
        try {
            return new BigInteger(num);
        } catch (NumberFormatException e) {
            throw new NumberFormat("Wrong number format", 0);
        }
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) throws ExpressionException {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) throws ExpressionException {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) throws ExpressionException {
        return x.multiply(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) throws ExpressionException {
        return x.divide(y);
    }

    @Override
    public BigInteger not(BigInteger x) throws ExpressionException {
        return x.not();
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) throws ExpressionException {
        return x.min(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) throws ExpressionException {
        return x.max(y);
    }
}
