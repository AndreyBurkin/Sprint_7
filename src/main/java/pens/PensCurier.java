package pens;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serializationJson.Courier;

import static io.restassured.RestAssured.given;

public class PensCurier {

    private final static String CREATE_PENS_COURIER = "/api/v1/courier";
    private final static String LOGIN_PENS_COURIER = "/api/v1/courier/login";
    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    //Ручка для создания курьера
    @Step("Создать курьера, проверить код ответа")
    public Response creatingCourier() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_PENS_COURIER);
        return response;
    }

    //Ручка для логирования курьера
    @Step("Авторизоваться курьером и получить id, проверить код ответа")
    public Response loginCurier() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PENS_COURIER);
        return response;
    }

    //Ручка для запроса id
    @Step("Запрос Id")
    public Response getId() {
        Response response = given()
                .get(CREATE_PENS_COURIER) // отправка GET-запроса
                .then().extract().body().path("id");
        return response;
    }

    //Ручка для удаления курьера
    @Step("Удалить курьера")
    public void deletingCourier() {
        Integer id = given().header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PENS_COURIER)
                .then().extract().body().path("id");
        if (id != null) {
            given().delete("/api/v1/courier/" + id);
        }
    }
}




