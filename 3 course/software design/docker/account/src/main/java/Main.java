import dao.AccountDaoImpl;
import http.AccountControllerImpl;
import http.ExchangeControllerImpl;
import io.reactivex.netty.protocol.http.server.HttpServer;

public class Main {
    public static void main(final String[] args) {
        HttpServer
                .newServer(8081)
                .start((req, resp) -> resp.writeString(
                        new AccountControllerImpl(
                                new AccountDaoImpl(
                                        new ExchangeControllerImpl()
                                )
                        )
                                .getResponse(req)
                                .map(r -> r + System.lineSeparator())))
                .awaitShutdown();
    }
}