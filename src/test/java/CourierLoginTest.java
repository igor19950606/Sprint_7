import client.Courier;
import client.Credentials;
import client.ScooterServicesClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTest {
    private ScooterServicesClient client = new ScooterServicesClient();
    private Courier courier;

    @Before
    public void before() {
        courier = new Courier("Testovvv", "1234", "Testov");
        client.createCourier(courier);
    }

    @After
    public void dataCleaning() {
        Integer idCourier = client.loginCourier(Credentials.fromCourier(courier))
                .then()
                .extract()
                .path("id");
        if (idCourier != null) {
            client.deleteCourier(idCourier.toString());
        }
    }

    @Test
    @Step("Авторизация курьера")
    public void courierLoginSuccess() {
        Response response = client.loginCourier(Credentials.fromCourier(courier));
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String courierId = response.then().extract().jsonPath().getString("id");
        assertThat(courierId, is(notNullValue()));
    }

    @Test
    @Step("Авторизация с несуществующим логином и паролем")
    public void courierLoginWithInvalidCredentials() {
        Credentials invalidCredentials = new Credentials("qwertyuiopl", "789654126");
        Response response = client.loginCourier(invalidCredentials);
        response.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
        response.then().assertThat().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @Step("Авторизация с отсутствующим логином")
    public void courierLoginWithMissingLogin() {
        Credentials credentialsWithoutLogin = new Credentials("", "12344");
        Response response = client.loginCourier(credentialsWithoutLogin);
        response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().assertThat().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @Step("Авторизация с отсутствующим паролем")
    public void courierLoginWithMissingPassword() {
        Credentials credentialsWithoutPassword = new Credentials("Testovvv", "");
        Response response = client.loginCourier(credentialsWithoutPassword);
        response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().assertThat().body("message", is("Недостаточно данных для входа"));
    }
}
