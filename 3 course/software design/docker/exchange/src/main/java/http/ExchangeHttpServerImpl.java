package http;

import dao.ExchangeDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Shares;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static http.HttpUtils.*;

public class ExchangeHttpServerImpl implements ExchangeHttpServer {
    public static final String COUNT = "count";
    public static final String COMPANY_NAME = "company_name";
    public static final String NAME = "name";
    public static final String SHARES_COUNT = "shares_count";
    public static final String SHARES_PRICE = "shares_price";
    public static final String NEW_SHARES_PRICE = "new_shares_price";

    public final ExchangeDao dao;

    public ExchangeHttpServerImpl(final ExchangeDao dao) {
        this.dao = dao;
    }

    @Override
    public <T> Observable<String> getResponse(final HttpServerRequest<T> request) {
        return switch (request.getDecodedPath().substring(1)) {
            case "add_company" -> addCompany(request);
            case "get_companies" -> getCompanies(request);
            case "sell_shares" -> sellShares(request);
            case "get_shares_price" -> getSharesPrice(request);
            case "get_shares_count" -> getSharesCount(request);
            case "buy_shares" -> buyShares(request);
            case "change_shares_price" -> changeSharesPrice(request);
            default -> Observable.just("Unsupported");
        };
    }

    public <T> Observable<String> addCompany(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(NAME, SHARES_COUNT, SHARES_PRICE))
                .map(Observable::just)
                .orElseGet(() -> dao.addCompany(
                                getParsedUrlParam(request, NAME),
                                getIntParam(request, SHARES_COUNT),
                                getIntParam(request, SHARES_PRICE)
                        )
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> getCompanies(final HttpServerRequest<T> request) {
        return dao.getCompanies().map(Objects::toString).reduce("", (s1, s2) -> s1 + ",\n" + s2);
    }

    public <T> Observable<String> sellShares(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(COMPANY_NAME, COUNT))
                .map(Observable::just)
                .orElseGet(() -> dao.addShares(
                                getParsedUrlParam(request, COMPANY_NAME),
                                getIntParam(request, COUNT)
                        )
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));
    }

    public <T> Observable<String> buyShares(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(COMPANY_NAME, COUNT))
                .map(Observable::just)
                .orElseGet(() -> dao.buyShares(
                                getParsedUrlParam(request, COMPANY_NAME),
                                getIntParam(request, COUNT)
                        )
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> getSharesPrice(final HttpServerRequest<T> request) {
        return check(request, Collections.singletonList(COMPANY_NAME))
                .map(Observable::just)
                .orElseGet(() -> dao.getShares(getParsedUrlParam(request, COMPANY_NAME))
                        .map(Shares::getPrice)
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> getSharesCount(final HttpServerRequest<T> request) {
        return check(request, Collections.singletonList(COMPANY_NAME))
                .map(Observable::just)
                .orElseGet(() -> dao.getShares(getParsedUrlParam(request, COMPANY_NAME))
                        .map(Shares::getCount)
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));

    }

    public <T> Observable<String> changeSharesPrice(final HttpServerRequest<T> request) {
        return check(request, Arrays.asList(COMPANY_NAME, NEW_SHARES_PRICE))
                .map(Observable::just)
                .orElseGet(() -> dao.changeSharesPrice(
                                getParsedUrlParam(request, COMPANY_NAME),
                                getIntParam(request, NEW_SHARES_PRICE)
                        )
                        .map(Objects::toString)
                        .onErrorReturn(Throwable::getMessage));
    }
}