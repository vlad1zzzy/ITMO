package expression;

import expression.generic.TripleExpression;

public class Const<T> implements TripleExpression<T> {
    private T num;

    public Const(T num) {
        this.num = num;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return num;
    }
}
