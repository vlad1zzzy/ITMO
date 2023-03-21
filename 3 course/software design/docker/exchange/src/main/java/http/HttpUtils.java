package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.util.List;
import java.util.Optional;

public class HttpUtils {
    public static <T> String getParsedUrlParam(final HttpServerRequest<T> request, final String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> int getIntParam(final HttpServerRequest<T> request, final String param) {
        return Integer.parseInt(getParsedUrlParam(request, param));
    }

    public static <T> Optional<String> check(final HttpServerRequest<T> request, final List<String> requiredParameters) {
        return requiredParameters.stream()
                .filter(key -> !request.getQueryParameters().containsKey(key))
                .reduce((s1, s2) -> s1 + ", " + s2);
    }
}
