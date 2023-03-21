package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public interface ExchangeHttpServer {
    <T> Observable<String> getResponse(HttpServerRequest<T> request);
}