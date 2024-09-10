package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderServices {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String POST_CREATE = "/api/v1/orders";
    private static final String GET_ORDER_BY_TRACK = "/api/v1/orders/track";


    public Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
                .post(POST_CREATE);
    }

    public Response getOrderByTrack(String trackNumber) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .param("t", trackNumber)
                .get(GET_ORDER_BY_TRACK);
    }
}

