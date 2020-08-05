package expression.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Source {
    boolean hasNext();
    char next();
    JsonException error(final String message);
}
