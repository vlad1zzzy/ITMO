import com.mongodb.rx.client.MongoClients;
import dao.ReactiveDaoImpl;
import http.ReactiveControllerImpl;
import io.reactivex.netty.protocol.http.server.HttpServer;

public class Main {
    public static void main(final String[] args) {
        final var database = MongoClients
                .create("mongodb://localhost:27017")
                .getDatabase("shop");

        HttpServer
                .newServer(8080)
                .start((req, resp) -> resp.writeString(
                        new ReactiveControllerImpl(
                                new ReactiveDaoImpl(
                                        database.getCollection("customers"),
                                        database.getCollection("products")
                                )
                        ).getResponse(req)))
                .awaitShutdown();
    }
}