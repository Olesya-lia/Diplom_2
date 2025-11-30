import base.BaseUserTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import steps.OrderSteps;

import java.util.Collections;
import java.util.List;

import static data.TestData.INVALID_INGREDIENT_HASH;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

public class OrderTest extends BaseUserTest {

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Успешное создание заказа возвращает код 200")
    public void testCreateOrderWithAuth() {
        List<String> ingredients = OrderSteps.getActualIngredients();
        OrderModel order = new OrderModel(ingredients.subList(0, 3));
        Response response = OrderSteps.createOrderWithAuth(order, tokenUser);
        response.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Успешное создание заказа без возвращает код 200")
    public void testCreateOrderWithoutAuth() {
        List<String> ingredients = OrderSteps.getActualIngredients();
        OrderModel order = new OrderModel(ingredients.subList(0, 3));
        Response response = OrderSteps.createOrderWithoutAuth(order);
        response.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Успешное создание заказа с ингредиентами возвращает код 200")
    public void testCreateOrderWithIngredients() {
        List<String> ingredients = OrderSteps.getActualIngredients();
        OrderModel order = new OrderModel(ingredients.subList(0,2));
        Response response = OrderSteps.createOrderWithAuth(order, tokenUser);
        response.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.ingredients", hasSize(2));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Создание заказа без ингредиентами возвращает код 400")
    public void testCreateOrderWithoutIngredients() {
        OrderModel order = new OrderModel(Collections.emptyList());
        Response response = OrderSteps.createOrderWithAuth(order, tokenUser);
        response.then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Создание заказа с неверным хешем ингредиентов код 500")
    public void testCreateOrderWithInvalidIngredientHash() {
        OrderModel order = new OrderModel(INVALID_INGREDIENT_HASH);
        Response response = OrderSteps.createOrderWithAuth(order, tokenUser);
        response.then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}