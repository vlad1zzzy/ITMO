import dao.AccountDao;
import dao.AccountDaoImpl;
import http.ExchangeControllerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    @ClassRule
    public static GenericContainer marketContainer = new FixedHostPortGenericContainer<>("exchange:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    private AccountDao dao;

    private final static String TEST_COMPANY_NAME = "test_company";
    private final static int TEST_COMPANY_SHARES_PRICE = 9;
    private final long TEST_CUSTOMER_ID = 7;

    @Before
    public void init() throws Exception {
        marketContainer.start();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/" +
                        "add_company" +
                        "?" +
                        "name=" + TEST_COMPANY_NAME +
                        "&" +
                        "shares_count=1000" +
                        "&" +
                        "shares_price=" + TEST_COMPANY_SHARES_PRICE))
                .GET()
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        dao = new AccountDaoImpl(new ExchangeControllerImpl());
        dao.addCustomer(TEST_CUSTOMER_ID);
        dao.addCash(TEST_CUSTOMER_ID, 999);
    }

    @After
    public void tearDown() {
        marketContainer.stop();
    }

    @Test
    public void buyShares() {
        assertThat(
                dao
                        .buyShares(TEST_CUSTOMER_ID, TEST_COMPANY_NAME, 3)
                        .toBlocking()
                        .single()
        ).isEqualTo("SUCCESS");
    }

    @Test
    public void sellShares() {
        assertThat(
                dao
                        .sellShares(TEST_CUSTOMER_ID, TEST_COMPANY_NAME, 3)
                        .toBlocking()
                        .single()
        ).isEqualTo("SUCCESS");
    }

    @Test
    public void notExistingCompany() {
        assertThat(
                dao
                        .buyShares(TEST_CUSTOMER_ID, "qwerty", 3)
                        .toBlocking()
                        .single()
        ).isEqualTo("Company qwerty doesn't exists");
    }

    @Test
    public void notEnoughShares() {
        assertThat(
                dao
                        .buyShares(TEST_CUSTOMER_ID, TEST_COMPANY_NAME, 1000000)
                        .toBlocking()
                        .single()
        ).isEqualTo("Not enough shares");
    }

    @Test
    public void notEnoughMoney() {
        assertThat(
                dao
                        .buyShares(TEST_CUSTOMER_ID, TEST_COMPANY_NAME, 55)
                        .toBlocking()
                        .single()
        ).isEqualTo("Not enough money");
    }
}