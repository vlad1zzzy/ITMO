package expression;

import expression.exceptions.ExpressionException;
import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

import java.util.Objects;

abstract class AbstractFunction<T> implements TripleExpression<T> {
    private final TripleExpression<T> first;
    private final TripleExpression<T> second;
    protected Operation<T> type;

    AbstractFunction(TripleExpression<T> first, TripleExpression<T> second, Operation <T> type) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    public String toString() {
        return "(" + first.toString() + " " +
                "op" +
                " " + second.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AbstractFunction comp = (AbstractFunction) object;
        return object == this || first.equals(comp.first) && second.equals(comp.second);
    }

    public int hashCode() {
        return 4241 * first.hashCode() + 53 * (Objects.hash("op")) + second.hashCode();
    }


    @Override
    public T evaluate(T x, T y, T z) throws ExpressionException {
        T left = first.evaluate(x, y, z);
        T right = second.evaluate(x, y, z);
        return calculate(left, right);
    }

    protected abstract T calculate(T x, T y) throws ExpressionException;
}
