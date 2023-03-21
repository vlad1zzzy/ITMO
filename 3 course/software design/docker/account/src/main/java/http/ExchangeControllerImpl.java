package http;

import java.util.Map;

import static http.HttpUtils.send;

public class ExchangeControllerImpl implements ExchangeController {
    @Override
    public void buyShares(final String companyName, final int count) {
        processShares(companyName, count, "buy_shares");
    }

    @Override
    public void sellShares(final String companyName, final int count) {
        processShares(companyName, count, "sell_shares");
    }

    @Override
    public int getSharesPrice(final String companyName) {
        return processShares(companyName, "get_shares_price");
    }

    @Override
    public int getSharesCount(final String companyName) {
        return processShares(companyName, "get_shares_count");
    }

    public void processShares(final String companyName, final int count, final String method) {
        send(method, Map.ofEntries(Map.entry("company_name", companyName), Map.entry("count", count)));
    }

    public int processShares(final String companyName, final String method) {
        return Integer.parseInt(send(method, Map.ofEntries(Map.entry("company_name", companyName))));
    }
}