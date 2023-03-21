package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private final long id;
    private int money;
    private final Map<String, Shares> shares = new HashMap<>();

    public Customer(final long id, final int money) {
        this.id = id;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(final int value) {
        money += value;
    }

    public Collection<Shares> getShares() {
        return shares.values();
    }

    public void buyShares(final String companyName, final int price, final int count) {
        if (price * count > money) {
            throw new IllegalArgumentException("Not enough money");
        }
        shares.put(companyName, shares.getOrDefault(companyName, new Shares(companyName, 0, 0)).add(count));
        money -= price * count;
    }

    public void sellShares(final String companyName, final int price, final int count) {
        if (!shares.containsKey(companyName) || shares.get(companyName).getCount() < count) {
            throw new IllegalArgumentException("Not enough shares");
        }
        shares.put(companyName, shares.get(companyName).subtract(count));
        money += price * count;
    }

    @Override
    public String toString() {
        return "Customer {\n" +
                "   id :    " + id + ",\n" +
                "   money : " + money + ",\n" +
                "}";
    }
}