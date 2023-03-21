package dao;

import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import rx.Observable;

public class DaoUtils {
    public static Observable<Document> getObservable(final MongoCollection<Document> collection, final Bson f) {
        return collection
                .find(f)
                .toObservable();
    }

    public static Observable<Document> getObservable(final MongoCollection<Document> collection) {
        return collection
                .find()
                .toObservable();
    }
}
