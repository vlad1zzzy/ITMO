package http;

import dao.AccountDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static http.HttpUtils.*;

public class AccountControllerImpl implements AccountController {
    public static final String ID = "id";
    public static final String COUNT = "count";
    public static final String COMPANY_NAME = "company_name";

    private final AccountDao dao;

    public AccountControllerImpl(final AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public <T> Observable<String> getResponse(final HttpServerRequest<T> request) {
        return switch (request.getDecodedPath().substring(1)) {
            case "add_customer" -> addCustomer(request);
            case "add_cash" -> addCash(request);
            case "get_shares" -> getShares(request);
            case "get_cash" -> getCash(request);
            case "buy_shares" -> buyShares(request);
            case "sell_shares" -> sellShares(request);
            default -> Observable.just("Unsupported");
        };
    }

    public <T> Observable<String> addCustomer(final HttpServerRequest<T> request) {
        return check(request, Collections.singletonList(ID))
                .map(Observable::just)
                .orElseGet(() -> dao.addCustomer(getLongParam(request, ID))
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> addCash(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(ID, COUNT))
                .map(Observable::just)
                .orElseGet(() -> dao.addCash(getLongParam(request, ID), getIntParam(request, COUNT))
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> getShares(final HttpServerRequest<T> request) {
        return check(request, Collections.singletonList(ID))
                .map(Observable::just)
                .orElseGet(() -> dao.getShares(getLongParam(request, ID))
                        .map(Objects::toString)
                        .reduce("", (s1, s2) -> s1 + "," + s2));

    }

    public <T> Observable<String> getCash(final HttpServerRequest<T> request) {
        return check(request, Collections.singletonList(ID))
                .map(Observable::just)
                .orElseGet(() -> dao.getCash(getLongParam(request, ID))
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> buyShares(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(ID, COMPANY_NAME, COUNT))
                .map(Observable::just)
                .orElseGet(() -> dao.buyShares(
                        getLongParam(request, ID),
                        getParsedUrlParam(request, COMPANY_NAME),
                        getIntParam(request, COUNT)
                ).map(Objects::toString).onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> sellShares(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(ID, COMPANY_NAME, COUNT))
                .map(Observable::just)
                .orElseGet(() -> dao.sellShares(
                        getLongParam(request, ID),
                        getParsedUrlParam(request, COMPANY_NAME),
                        getIntParam(request, COUNT)
                ).map(Objects::toString).onErrorReturn(Throwable::getMessage));

    }
}