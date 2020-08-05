package expression.generic.operations;

import expression.exceptions.ExpressionException;

public interface Operation<T> {
    T add(T x, T y) throws ExpressionException;

    T sub(T x, T y) throws ExpressionException;

    T mul(T x, T y) throws ExpressionException;

    T div(T x, T y) throws ExpressionException;

    T not(T x) throws ExpressionException;

    T min(T x, T y) throws ExpressionException;

    T max(T x, T y) throws ExpressionException;

    T parseNum(String x) throws ExpressionException;

    //T count(T x) throws ExpressionException;
}
