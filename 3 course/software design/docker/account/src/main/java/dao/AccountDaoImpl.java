package dao;

import com.mongodb.rx.client.Success;
import http.ExchangeController;
import model.Customer;
import model.Shares;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class AccountDaoImpl implements AccountDao {
    private final ExchangeController exchangeController;
    private final Map<Long, Customer> customers = new HashMap<>();

    public AccountDaoImpl(final ExchangeController exchangeController) {
        this.exchangeController = exchangeController;
    }

    @Override
    public Observable<Success> addCustomer(final long id) {
        final Observable<Success> error = isCustomerExists(id);

        if (error == null) {
            customers.put(id, new Customer(id, 0));
            return Observable.just(Success.SUCCESS);
        }

        return error;
    }

    @Override
    public Observable<Success> addCash(final long id, final int count) {
        final Observable<Success> error = isCustomerNotExists(id);

        if (error == null) {
            customers.get(id).addMoney(count);
            return Observable.just(Success.SUCCESS);
        }

        return error;
    }

    @Override
    public Observable<Shares> getShares(final long id) {
        final Observable<Shares> error = isCustomerNotExists(id);

        if (error == null) {
            return Observable.from(customers.get(id).getShares());
        }

        return error;
    }

    @Override
    public Observable<Integer> getCash(final long id) {
        final Observable<Integer> error = isCustomerNotExists(id);

        if (error == null) {
            final Customer customer = customers.get(id);
            return Observable.from(customer.getShares())
                    .map(Shares::getPrice)
                    .defaultIfEmpty(0)
                    .reduce(Integer::sum)
                    .map(x -> x + customer.getMoney());
        }

        return error;
    }

    @Override
    public Observable<Success> buyShares(final long id, final String companyName, final int count) {
        final Observable<Success> error = isCustomerNotExists(id);

        if (error == null) {
            if (count > exchangeController.getSharesCount(companyName)) {
                return Observable.error(new IllegalArgumentException("Not enough shares"));
            }

            customers.get(id).buyShares(companyName, exchangeController.getSharesPrice(companyName), count);
            exchangeController.buyShares(companyName, count);
            return Observable.just(Success.SUCCESS);
        }

        return error;
    }

    @Override
    public Observable<Success> sellShares(final long id, final String companyName, final int count) {
        final Observable<Success> error = isCustomerNotExists(id);

        if (error == null) {
            customers.get(id).sellShares(companyName, exchangeController.getSharesPrice(companyName), count);
            exchangeController.sellShares(companyName, count);
            return Observable.just(Success.SUCCESS);
        }

        return error;
    }

    public <T> Observable<T> isCustomerExists(final long id) {
        if (customers.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("Customer " + id + " already exists"));
        }

        return null;
    }

    public <T> Observable<T> isCustomerNotExists(final long id) {
        if (!customers.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("Customer " + id + " doesn't exist"));
        }

        return null;
    }
}