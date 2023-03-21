package http;

public interface ExchangeController {
    void buyShares(String companyName, int count);

    void sellShares(String companyName, int count);

    int getSharesPrice(String companyName);

    int getSharesCount(String companyName);
}