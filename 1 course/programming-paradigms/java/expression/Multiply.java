package expression;

import expression.exceptions.ExpressionException;
import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

public class Multiply<T> extends AbstractFunction<T> {

    public Multiply(TripleExpression<T> first, TripleExpression<T> second, Operation<T> type) {
        super(first, second, type);
    }

    protected T calculate(T x, T y) throws ExpressionException {
        return type.mul(x, y);
    }
}
