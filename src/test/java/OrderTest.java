import client.Order;
import client.OrderServices;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

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

        ValidatableResponse response = client.createOrder(order);
        response.assertThat()
                .statusCode(201)
                .body("track", notNullValue());
        trackNumber = response.extract().path("track").toString();
    }

    @Test
    @Step("Проверка, что в тело ответа возвращается список заказов")
    public void getOrderByTrack() {
        ValidatableResponse response = client.getOrderByTrack(trackNumber);
        response.assertThat()
                .statusCode(200)
                .body("order", notNullValue())
                .body("order.track", equalTo(Integer.parseInt(trackNumber)))
                .log().all();
    }
}