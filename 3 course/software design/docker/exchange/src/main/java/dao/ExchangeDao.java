package dao;

import com.mongodb.rx.client.Success;
import model.Shares;
import rx.Observable;

public interface ExchangeDao {
    Observable<Success> addCompany(String name, int sharesCount, int sharesPrice);

    Observable<Shares> getCompanies();

    Observable<Success> addShares(String companyName, int sharesCount);

    Observable<Shares> getShares(String companyName);

    Observable<Success> buyShares(String companyName, int count);

    Observable<Success> changeSharesPrice(String companyName, int newSharesPrice);
}