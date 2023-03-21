package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

public class HttpUtils {
    public static <T> String getParsedUrlParam(final HttpServerRequest<T> request, final String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> long getLongParam(final HttpServerRequest<T> request, final String param) {
        return Long.parseLong(getParsedUrlParam(request, param));
    }

    public static <T> double getDoubleParam(final HttpServerRequest<T> request, final String param) {
        return Double.parseDouble(getParsedUrlParam(request, param));
    }
}