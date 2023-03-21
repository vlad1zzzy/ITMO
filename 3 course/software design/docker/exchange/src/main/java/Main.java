import com.mongodb.rx.client.MongoClients;
import dao.ExchangeDaoImpl;
import http.ExchangeHttpServerImpl;
import io.reactivex.netty.protocol.http.server.HttpServer;


public class Main {
    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> resp.writeString(
                        new ExchangeHttpServerImpl(
                                new ExchangeDaoImpl(
                                        MongoClients
                                                .create("mongodb://localhost:27017")
                                                .getDatabase("exchange")
                                                .getCollection("companies")
                                )
                        )
                                .getResponse(req)
                                .map(r -> r + System.lineSeparator())))
                .awaitShutdown();
    }
}