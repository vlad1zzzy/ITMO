package http;

import com.mongodb.rx.client.Success;
import dao.ReactiveDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Customer;
import model.Product;
import rx.Observable;

import static http.HttpUtils.*;

public class ReactiveControllerImpl implements ReactiveController {
    public static final String ID = "id";
    public static final String CURRENCY = "currency";
    public static final String PRICE = "price";

    private final ReactiveDao dao;

    public ReactiveControllerImpl(final ReactiveDao dao) {
        this.dao = dao;
    }

    @Override
    public <T> Observable<String> getResponse(final HttpServerRequest<T> request) {
        final var response = switch (request.getDecodedPath().substring(1)) {
            case "add_customer" -> addCustomer(request);
            case "add_product" -> addProduct(request);
            case "get_customer_products" -> getCustomerProducts(request);
            case "get_customers" -> getCustomers();
            default -> Observable.just("Unsupported");
        };

        return response.map(Object::toString);
    }

    private <T> Observable<Success> addCustomer(final HttpServerRequest<T> request) {
        return dao
                .addCustomer(
                        new Customer(
                                getLongParam(request, ID),
                                getParsedUrlParam(request, CURRENCY)));
    }

    private <T> Observable<Success> addProduct(final HttpServerRequest<T> request) {
        return dao
                .addProduct(
                        new Product(
                                getLongParam(request, ID),
                                getParsedUrlParam(request, CURRENCY),
                                getDoubleParam(request, PRICE)));
    }

    private <T> Observable<Product> getCustomerProducts(final HttpServerRequest<T> request) {
        return dao
                .getCustomerProducts(
                        getLongParam(request, ID));
    }

    private Observable<String> getCustomers() {
        return dao.getCustomers().map(Object::toString);
    }
}