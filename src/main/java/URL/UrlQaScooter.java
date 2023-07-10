package URL;

import io.restassured.RestAssured;

public class UrlQaScooter {

        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        }

}
