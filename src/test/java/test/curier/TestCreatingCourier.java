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

public class TestCreatingCourier {

    public PensCurier pensCurier = new PensCurier();
    public UrlQaScooter baseURL = new UrlQaScooter();

    // URL куда ходить
    @Before
    public void setupBaseUrl() {
        baseURL.setUp();
    }

    // очистка БД от созданных данных
    @After
    public void deleteCourier() {
        pensCurier.deletingCourier();
    }

    // Позитивный кейс, создание курьера
    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с валидным телом запроса")
    public void creatingACourier() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier().then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    // Негативный кейс - "Нельзя создать 2 одинаковых курьеров"
    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("Попытка создать курьеров с одинаковыми логинами")
    public void creatingACourierTwice() {
        Courier courier = new Courier("Kolovrat", "123", "Efpatiy");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        pensCurier.creatingCourier().then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    // Валидация обязательных полей в теле запроса, поле "login" = null
    @Test
    @DisplayName("Создание курьера без поля null")
    @Description("Проверка валидации, создание курьера со значение обязательного поля \"login\" = null")
    public void createACourierWithoutAPassword() {
        Courier courier = new Courier(null, "123", "Gambrilius");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier().then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    // Валидация обязательных полей, в теле запроса пустое значение поля "password"
    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка невозможности создания курьера без пароля")
    public void createACourierWithoutALogin() {
        Courier courier = new Courier("Markus", "", "Gambrilius");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier().then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    // Валидация обязательных полей, в теле запроса отсутствует поле "firstName"
    @Test
    @DisplayName("Создание курьера без Имени")
    @Description("Проверка возможности создание курьера без параметра \"firstName\" в теле запроса")
    public void createACourierWithoutAName() {
        Courier courier = new Courier("Markus", "123");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier().then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    // Тестирование невозможности создать двух одинаковых пользователей через массив
    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров")
    @Description("Проверка невозможности создания двух курьеров с одинаковым значением параметра \"login\"")
    public void creationACourierWithARepeatedLogin() {
        Courier courier = new Courier("Markus", "12345", "Gambrilius");
        pensCurier.setCourier(courier);
        pensCurier.creatingCourier();
        Courier courier1 = new Courier("Markus", "12345", "Gambrilius");
        pensCurier.setCourier(courier1);
        pensCurier.creatingCourier().then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
