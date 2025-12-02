package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserModel;

import static constants.Endpoint.*;
import static io.restassured.RestAssured.given;

public class UserSteps {

    @Step("Создание пользователя")
    public static Response createUser(UserModel userModel){
        return given()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .extract().response();
    }

    @Step("Авторизация пользователя")
    public static Response loginUser(UserModel userModel) {
        return given()
                .with()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(LOGIN_USER_PATH)
                .then()
                .extract().response();
    }

    @Step("Удаление пользователя c помощью token")
    public static Response deleteUser(String tokenUser) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization",tokenUser)
                .when()
                .delete(DELETE_USER_PATH)
                .then()
                .log().all()
                .extract().response();
    }
}
