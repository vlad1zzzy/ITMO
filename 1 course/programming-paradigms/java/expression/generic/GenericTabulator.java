package expression.generic;

import expression.exceptions.ExpressionException;
import expression.generic.operations.*;
import expression.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {
    private static Operation<?> operationMode(String mode) throws ExpressionException {
        switch (mode) {
            case "i":
                return new IntOperation();
            case "d":
                return new DoubleOperation();
            case "bi":
                return new BigIntOperation();
            case "l":
                return new LongOperation();
            case "s":
                return new ShortOperation();
            default:
                throw new ExpressionException("Invalid type");
        }
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
        return tabulateT(operationMode(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulateT(Operation<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2)  throws ExpressionException {
        ExpressionParser<T> parser = new ExpressionParser<>(type);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        try {
            TripleExpression<T> parse = parser.parse(expression);
            for (int i = 0; i <= x2 - x1; i++) {
                for (int j = 0; j <= y2 - y1; j++) {
                    for (int k = 0; k <= z2 - z1; k++) {
                        try {
                            result[i][j][k] = parse.evaluate(
                                    type.parseNum(Integer.toString(x1 + i)),
                                    type.parseNum(Integer.toString(y1 + j)),
                                    type.parseNum(Integer.toString(z1 + k))
                            );
                        } catch (Exception e) {
                            result[i][j][k] = null;
                        }
                    }
                }
            }
            return result;
        } catch (ExpressionException e) {
            throw new ExpressionException("Parsing error");
        }
    }
}
