package expression;

import expression.exceptions.ExpressionException;
import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

abstract class AbstractFunctionUnary<T> implements TripleExpression<T> {
    private final TripleExpression<T> first;
    protected Operation<T> type;

    AbstractFunctionUnary(TripleExpression<T> first, Operation <T> type) {
        this.first = first;
        this.type = type;
    }

    @Override
    public T evaluate(T x, T y, T z) throws ExpressionException {
        return calculate(first.evaluate(x, y, z));
    }

    protected abstract T calculate(T x) throws ExpressionException;
}
