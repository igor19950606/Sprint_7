import client.Courier;
import client.Credentials;
import client.ScooterServicesClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CourierCreateTest {

    private ScooterServicesClient client = new ScooterServicesClient();
    private Courier courier;

    @After
    public void dataCleaning() {
        if (courier != null) {
            Integer idCourier = client.loginCourier(Credentials.fromCourier(courier))
                    .extract()
                    .path("id");
            if (idCourier != null) {
                client.deleteCourier(idCourier.toString());
            }
        }
    }

    @Test
    @Step("Создание курьера с заполнением всех полей")
    public void courierCreate() {
        courier = new Courier("Ivitro", "12324", "Ivitro12");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @Step("Создание курьера с заполнием обязательных полей")
    public void courierCreateRequired() {
        courier = new Courier("Ivitro", "1234", null);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @Step("Создание курьера с одинаковыми данными")
    public void courierCreateDouble() {
        Courier courier = new Courier("ninja", "1234", "saske");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Step("Создание курьера с не заполненным логином")
    public void courierCreateNullLogin() {
        courier = new Courier("", "12324", "Ivitro12");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Создание курьера с не заполненным паролем")
    public void courierCreateNullPassword() {
        courier = new Courier("Ivitro", "", "Ivitro12");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Создание курьера с логином который уже есть")
    public void courierCreateLogonDouble() {
        courier = new Courier("ninja", "1234", "Ivitro12");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
}