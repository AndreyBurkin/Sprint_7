package test.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pens.PensOrder;
import serializationJson.Order;
import url.UrlQaScooter;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class TestCreatingOrders {

    Order order;
    UrlQaScooter baseURL = new UrlQaScooter();
    PensOrder pensOrder = new PensOrder();

    public TestCreatingOrders(Order order) {
        this.order = order;
    }

    //Метод для получения данных
    @Parameterized.Parameters
    public static Object[][] getTestOrderData() {
        return new Object[][]{
                {new Order("Умный", "Пёс", "Будка, 18.", "4", "+7 800 355 35 35", 5, "2023-07-08", "Гав-гав", new String[]{"BLACK"})},
                {new Order("Весёлый", "Кот", "Дерево, 1.", "215", "+7 877 355 35 35", 4, "2023-07-08", "Мяу-мяу", new String[]{"GREY"})},
                {new Order("Червяк", "Джим", "Дом, 5.", "26", "+7 800 355 55 35", 10, "2023-07-08", "Крутой Джим", new String[]{"BLACK", "GREY"})},
                {new Order("Упыряка", "Забияка", "Дом, 5.", "26", "+7 800 355 55 35", 10, "2023-07-08", "Крутой Джим", new String[]{})},
        };
    }

    @Before
    public void setupBaseURL() {
        baseURL.setUp();
    }

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder() {
        pensOrder.setOrder(order);
        pensOrder.createOrderRequest()
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", Matchers.notNullValue());
    }
}
