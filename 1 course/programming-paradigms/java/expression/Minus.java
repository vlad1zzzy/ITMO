package expression;

import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

public class Minus<T> extends AbstractFunctionUnary<T> {

    public Minus(TripleExpression<T> first, Operation<T> type) {
        super(first, type);
    }

    protected T calculate(T x) {
        return type.not(x);
    }
}