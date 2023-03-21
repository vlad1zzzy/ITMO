package dao;

import com.mongodb.rx.client.Success;
import model.Customer;
import model.Product;
import rx.Observable;

public interface ReactiveDao {
    Observable<Success> addCustomer(Customer customer);

    Observable<Success> addProduct(Product product);

    Observable<Product> getCustomerProducts(long customerId);

    Observable<Customer> getCustomers();
}