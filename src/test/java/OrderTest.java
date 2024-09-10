import client.Order;
import client.OrderServices;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {

    private static OrderServices client = new OrderServices();
    private String[] colors;
    private static String trackNumber;

    public OrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return OrderParameters.data();
    }

    @Before
    public void setUp() {
        if (trackNumber == null) {
            createOrder();
        }
    }

    @Test
    @Step("Создание заказа")
    public void createOrder() {
        Order order = new Order(
                "Test",
                "Testerov",
                "USSR",
                10,
                "+88005553535",
                10,
                "2000-01-01",
                "No comment",
                colors
        );

        Response response = client.createOrder(order);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
        trackNumber = response.path("track").toString();
    }
}