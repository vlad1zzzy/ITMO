package expression;

import expression.exceptions.ExpressionException;
import expression.generic.TripleExpression;

public class Variable<T> implements TripleExpression<T> {
    private String var;

    public Variable(String var) {
        this.var = var;
    }


    @Override
    public T evaluate(T x, T y, T z) {
        switch (var) {
            case("x"):
                return x;
            case("y"):
                return y;
            case("z"):
                return z;
        }
        throw new ExpressionException("Wrong variable");
    }
}
