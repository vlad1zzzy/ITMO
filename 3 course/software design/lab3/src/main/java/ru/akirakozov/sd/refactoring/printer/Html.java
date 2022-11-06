package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.model.Product;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Html {
    public static final String HtmlStart = "<html><body>";
    public static final String HtmlEnd = "</body></html>";

    public static final String HtmlTitleStart = "<h1>";
    public static final String HtmlTitleEnd = "</h1>";

    public static String getBodyFormat(final String... inner) {
        return getBaseFormat(HtmlStart, HtmlEnd, inner);
    }

    public static String getTitleFormat(final String... inner) {
        return getBaseFormat(HtmlTitleStart, HtmlTitleEnd, inner);
    }

    private static String getBaseFormat(final String start, final String end, final String... inner) {
        return String.format(
                "%s%s%s",
                start,
                Arrays.stream(inner).map(String::toString).collect(Collectors.joining()),
                end
        );
    }

    public static void printProducts(final List<Product> products, final PrintWriter printer) {
        printer.println(getBodyFormat(products.stream().map(Product::toHTML).collect(Collectors.joining())));
    }

    public static void printProduct(final Optional<Product> product, final String header, final PrintWriter printer) {
        product.ifPresent(value -> printer.println(getBodyFormat(getTitleFormat(header), value.toHTML())));
    }

    public static void printInfo(final Object info, final String header, final PrintWriter printer) {
        printer.println(getBodyFormat(header + info));
    }
}