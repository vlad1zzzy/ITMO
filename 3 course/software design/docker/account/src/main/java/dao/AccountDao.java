package dao;

import com.mongodb.rx.client.Success;
import model.Shares;
import rx.Observable;

public interface AccountDao {
    Observable<Success> addCustomer(long id);

    Observable<Success> addCash(long id, int count);

    Observable<Integer> getCash(long id);

    Observable<Success> buyShares(long id, String companyName, int count);

    Observable<Success> sellShares(long id, String companyName, int count);

    Observable<Shares> getShares(long id);
}