import client.Courier;
import client.Credentials;
import client.ScooterServicesClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CourierLoginTest {
    private ScooterServicesClient client = new ScooterServicesClient();
    private Courier courier;

    @Before
    public void before(){
        courier = new Courier("Testovvv", "1234", "Testov");
        client.createCourier(courier);
    }

    @After
    public void dataCleaning() {
        Integer idCourier = client.loginCourier(Credentials.fromCourier(courier))
                .extract()
                .path("id");
        if (idCourier != null) {
            client.deleteCourier(idCourier.toString());
        }
    }

    @Test
    @Step("Авторизация курьера")
    public void courierLoginSuccess(){
        ValidatableResponse response = client.loginCourier(Credentials.fromCourier(courier));
response.assertThat().statusCode(200);
        String courierId = response.extract().jsonPath().getString("id");

    }
    @Test
    @Step("Авторизация с несуществующим логином и паролем")
    public void courierLoginWithInvalidCredentials() {
        Credentials invalidCredentials = new Credentials("qwertyuiopl", "789654126");
        ValidatableResponse response = client.loginCourier(invalidCredentials);
        response.log().all();
        response.assertThat().statusCode(404);
        response.assertThat().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @Step("Авторизация с отсутствующим логином")
    public void courierLoginWithMissingLogin() {
        Credentials credentialsWithoutLogin = new Credentials("", "12344");
        ValidatableResponse response = client.loginCourier(credentialsWithoutLogin);
        response.log().all();
        response.assertThat().statusCode(400);
        response.assertThat().body("message", is("Недостаточно данных для входа"));
    }
    @Test
    @Step("Авторизация с отсутствующим паролем")
    public void courierLoginWithMissingPassword() {
        Credentials credentialsWithoutLogin = new Credentials("Testovvv", "");
        ValidatableResponse response = client.loginCourier(credentialsWithoutLogin);
        response.log().all();
        response.assertThat().statusCode(400);
        response.assertThat().body("message", is("Недостаточно данных для входа"));
    }

}
