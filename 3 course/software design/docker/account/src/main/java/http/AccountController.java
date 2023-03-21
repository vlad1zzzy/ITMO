package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public interface AccountController {
    <T> Observable<String> getResponse(final HttpServerRequest<T> request);
}