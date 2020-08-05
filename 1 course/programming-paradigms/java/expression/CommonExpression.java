package expression;

import expression.generic.TripleExpression;

public interface CommonExpression extends Expression, DoubleExpression, TripleExpression {
    String toString();
}
