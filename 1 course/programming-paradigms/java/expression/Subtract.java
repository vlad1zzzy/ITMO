package expression;

import expression.exceptions.ExpressionException;
import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

public class Subtract<T> extends AbstractFunction<T> {

    public Subtract(TripleExpression<T> first, TripleExpression<T> second, Operation<T> type) {
        super(first, second, type);
    }

    protected T calculate(T x, T y) throws ExpressionException {
        return type.sub(x, y);
    }
}
