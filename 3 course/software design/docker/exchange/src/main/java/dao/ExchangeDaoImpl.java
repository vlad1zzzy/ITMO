package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.Shares;
import org.bson.Document;
import rx.Observable;
import rx.functions.Func2;

public class ExchangeDaoImpl implements ExchangeDao {
    public static final String COMPANY_NAME = "companyName";

    private final MongoCollection<Document> companies;

    public ExchangeDaoImpl(final MongoCollection<Document> companies) {
        this.companies = companies;
    }

    @Override
    public Observable<Success> addCompany(final String name, final int sharesCount, final int sharesPrice) {
        return companies
                .find(Filters.eq(COMPANY_NAME, name))
                .toObservable()
                .isEmpty()
                .flatMap(isEmpty -> isEmpty
                        ? companies.insertOne(new Shares(name, sharesCount, sharesPrice).toDocument())
                        : Observable.error(new IllegalArgumentException("Company " + name + " already exists")));
    }

    @Override
    public Observable<Shares> getCompanies() {
        return companies.find().toObservable().map(Shares::new);
    }

    @Override
    public Observable<Success> addShares(final String companyName, final int sharesCount) {
        return processShares(companyName, Shares::add, sharesCount);
    }

    @Override
    public Observable<Shares> getShares(final String companyName) {
        return companies
                .find(Filters.eq(COMPANY_NAME, companyName))
                .toObservable()
                .map(Shares::new);
    }

    @Override
    public Observable<Success> buyShares(final String companyName, final int count) {
        return processShares(companyName, Shares::subtract, count);
    }

    @Override
    public Observable<Success> changeSharesPrice(final String companyName, final int newSharesPrice) {
        return processShares(companyName, Shares::changePrice, newSharesPrice);
    }

    private Observable<Success> processShares(final String companyName, final Func2<Shares, Integer, Shares> action, final int parameter) {
        return companies
                .find(Filters.eq(COMPANY_NAME, companyName))
                .toObservable()
                .map(Shares::new)
                .defaultIfEmpty(null)
                .flatMap(company -> company == null
                        ? Observable.error(new Error("Company with name '" + companyName + "' doesn't exists"))
                        : companies
                        .replaceOne(Filters.eq(COMPANY_NAME, companyName), action.call(company, parameter).toDocument())
                        .map(d -> Success.SUCCESS));
    }
}