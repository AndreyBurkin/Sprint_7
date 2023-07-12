package test.curier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.PensCurier;
import serializationJson.Courier;
import url.UrlQaScooter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestAuthorizationLoginCurier {

    PensCurier pensCurier = new PensCurier();
    UrlQaScooter baseURL = new UrlQaScooter();

    // подключение к URL
    @Before
    public void setupBaseURL() {
        baseURL.setUp();
    }

    // очистка БД от созданных данных
    @After
    public void deleteCourier() {
        pensCurier.deletingCourier();
    }

    // авторизация курьера, успешный запрос возвращает id
    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверки возможности авторизаваться курьером при введении валидных данных, возвращения \"id\"")
    public void AutorizationСourier() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        Courier courier1 = new Courier("Kolovrat", "123");
        pensCurier.setCourier(courier1);
        pensCurier.loginCurier().then().assertThat().body("id", notNullValue()).
                and().statusCode(200);
    }

    // Валидация полей при авторизации, логирование с пустым "login"
    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка возможности авторизоваться с пустым значением поля \"login\"")
    public void loginACourierWithoutAPassword() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        Courier courier1 = new Courier("Kolovrat", "");
        pensCurier.setCourier(courier1);
        pensCurier.loginCurier().then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test // Проверка валидации логирование c пустым "password"
    @DisplayName("Валидация параметра \"password\"")
    @Description("Проверка возможности авторизоваться с пустым значением поля \"password\"")
    public void loginACourierWithoutALogin() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        Courier courier1 = new Courier("", "123");
        pensCurier.setCourier(courier1);
        pensCurier.loginCurier().then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    // Валидация полей при входе неверные логин и пароль; авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    @DisplayName("Неверные логин пароль")
    @Description("Проверка валидации параметров при входе, авторизация с не верными(не валидными) \"login\",\"password\"")
    public void loginWithWrongLogin() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        Courier courier1 = new Courier("Himir", "123");
        pensCurier.setCourier(courier1);
        pensCurier.loginCurier().then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
