import client.Order;
import client.OrderServices;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrderByTrackTest {

    private static OrderServices client = new OrderServices();
    private static String trackNumber;

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
                new String[]{}
        );

        Response response = client.createOrder(order);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
        trackNumber = response.path("track").toString();
    }

    @Test
    @Step("Проверка, что в тело ответа возвращается список заказов")
    public void getOrderByTrack() {
        Response response = client.getOrderByTrack(trackNumber);
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("order", notNullValue())
                .body("order.track", equalTo(Integer.parseInt(trackNumber)));
    }
}