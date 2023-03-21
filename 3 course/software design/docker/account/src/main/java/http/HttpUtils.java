package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpUtils {
    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080/";

    public static <T> String getParsedUrlParam(final HttpServerRequest<T> request, final String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> int getIntParam(final HttpServerRequest<T> request, final String param) {
        return Integer.parseInt(getParsedUrlParam(request, param));
    }

    public static <T> long getLongParam(final HttpServerRequest<T> request, final String param) {
        return Long.parseLong(getParsedUrlParam(request, param));
    }

    public static <T> Optional<String> check(final HttpServerRequest<T> request, final List<String> requiredParameters) {
        return requiredParameters.stream()
                .filter(key -> !request.getQueryParameters().containsKey(key))
                .reduce((s1, s2) -> s1 + "," + s2);
    }

    public static String send(final String path, final Map<String, Object> parameters) {
        try {
            final String requestString = HTTP_LOCALHOST_8080 + path + "?" + parameters.keySet().stream()
                    .map(param -> param + "=" + parameters.get(param))
                    .reduce((s1, s2) -> s1 + "&" + s2)
                    .orElse("");

            return HttpClient
                    .newHttpClient()
                    .send(
                            HttpRequest.newBuilder().uri(new URI(requestString)).GET().build(),
                            HttpResponse.BodyHandlers.ofString())
                    .body()
                    .trim();
        } catch (final IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}