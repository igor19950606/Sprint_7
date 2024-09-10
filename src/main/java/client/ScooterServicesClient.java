package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ScooterServicesClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_ENDPOINT = COURIER_ENDPOINT + "/login";
    public static final String DELETE_ENDPOINT = COURIER_ENDPOINT + "/{id}";

    // Создание курьера
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .post(COURIER_ENDPOINT);
    }

    // Авторизация курьера
    public Response loginCourier(Credentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(credentials)
                .post(LOGIN_ENDPOINT);
    }

    // Удаление курьера
    public Response deleteCourier(String id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .pathParam("id", id)
                .when()
                .delete(DELETE_ENDPOINT);
    }
}