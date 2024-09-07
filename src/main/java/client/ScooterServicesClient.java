package client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ScooterServicesClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public final String DELETE_ENDPOINT = "/api/v1/courier/{id}";


    //создание курьера
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .post("/api/v1/courier")
                .then()
                .log().all();
    }

    //Авторизация курьера
    public ValidatableResponse loginCourier(Credentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(credentials)
                .post("/api/v1/courier/login")
                .then()
                .log().all();
    }

    public ValidatableResponse deleteCourier(String id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .pathParam("id", id)
                .when()
                .delete(DELETE_ENDPOINT)
                .then()
                .statusCode(200)
                .body("ok", is(true))
                .log().all();
    }
}
