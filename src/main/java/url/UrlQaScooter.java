package url;

import io.restassured.RestAssured;

public class UrlQaScooter {

    public final void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

}
