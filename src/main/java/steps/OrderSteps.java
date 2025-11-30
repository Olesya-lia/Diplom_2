package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import java.util.List;

import static constants.Endpoint.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Получение заказов пользователя")
    public static Response getUserOrders(String tokenUser){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenUser)
                .when()
                .get(ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение списка ингредиентов")
    public static List<String> getActualIngredients(){
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(INGREDIENTS_ORDER_PATH)
                .then()
                .extract().response();
        return response.jsonPath().getList("data._id");
    }

    @Step("Создание заказа с авторизацией")
    public static Response createOrderWithAuth(OrderModel orderModel, String tokenUser){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenUser)
                .body(orderModel)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAuth(OrderModel orderModel){
        return given()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract().response();
    }
}
