package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.model.Product;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class Html {
    public static void printProducts(List<Product> products, PrintWriter printer) {
        printer.println("<html><body>");
        products.forEach(product -> printer.println(product.toHTML()));
        printer.println("</body></html>");
    }

    public static void printProduct(Optional<Product> product, String header, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println("<h1>" + header + "</h1>");
        product.ifPresent(p -> printer.println(p.toHTML()));
        printer.println("</body></html>");
    }

    public static void printInfo(Object info, String header, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println(header);
        printer.println(info);
        printer.println("</body></html>");
    }
}