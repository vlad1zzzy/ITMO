package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.Customer;
import model.Product;
import org.bson.Document;
import rx.Observable;

import static dao.DaoUtils.getObservable;

public class ReactiveDaoImpl implements ReactiveDao {
    public static final String ID = "id";
    public static final String CURRENCY = "currency";

    private final MongoCollection<Document> customers;
    private final MongoCollection<Document> products;

    public ReactiveDaoImpl(final MongoCollection<Document> customers, final MongoCollection<Document> products) {
        this.customers = customers;
        this.products = products;
    }

    @Override
    public Observable<Success> addCustomer(final Customer customer) {
        return insert(customer.toDocument(), customers);
    }

    @Override
    public Observable<Success> addProduct(final Product product) {
        return insert(product.toDocument(), products);
    }

    @Override
    public Observable<Product> getCustomerProducts(final long customerId) {
        return getObservable(customers, Filters.eq(ID, customerId))
                .flatMap(cus ->
                        getObservable(products)
                                .map(doc -> new Product(doc).convert(cus.getString(CURRENCY))));
    }

    @Override
    public Observable<Customer> getCustomers() {
        return getObservable(customers).map(Customer::new);
    }

    private Observable<Success> insert(final Document document, final MongoCollection<Document> collection) {
        return collection
                .find(Filters.eq(ID, document.getLong(ID)))
                .toObservable()
                .singleOrDefault(null)
                .flatMap(doc -> doc == null
                        ? collection.insertOne(document).asObservable()
                        : Observable.error(new Error("Already exists")));
    }
}