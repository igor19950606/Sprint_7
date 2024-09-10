import client.Courier;
import client.Credentials;
import client.ScooterServicesClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
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
                    .then()
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
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @Step("Создание курьера с заполнением обязательных полей")
    public void courierCreateRequired() {
        courier = new Courier("Ivitro", "1234", null);
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @Step("Создание курьера с одинаковыми данными")
    public void courierCreateDouble() {
        Courier courier = new Courier("ninja", "1234", "saske");
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Step("Создание курьера с не заполненным логином")
    public void courierCreateNullLogin() {
        courier = new Courier("", "12324", "Ivitro12");
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Создание курьера с не заполненным паролем")
    public void courierCreateNullPassword() {
        courier = new Courier("Ivitro", "", "Ivitro12");
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Создание курьера с логином, который уже есть")
    public void courierCreateLogonDouble() {
        courier = new Courier("ninja", "1234", "Ivitro12");
        Response response = client.createCourier(courier);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
}