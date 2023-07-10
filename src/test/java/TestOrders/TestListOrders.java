package TestOrders;

import Pens.PensOrder;
import URL.UrlQaScooter;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestListOrders {
  PensOrder pensOrder = new PensOrder();
  UrlQaScooter baseURL = new UrlQaScooter();
  @Before
  public void setupBaseURL(){
    baseURL.setUp();
  }
  @Test
  @DisplayName("Получение списка заказов(позитив)")
  @Description("Проверка получения списка заказов")
  public void checkCreateOrder(){
    pensOrder.getOrdersRequest()
            .then().statusCode(SC_OK)
            .and()
            .assertThat().body("orders", notNullValue());
  }
}

