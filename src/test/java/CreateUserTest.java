import base.BaseUserTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserModel;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.createUser;

public class CreateUserTest extends BaseUserTest {

    @Test
    @DisplayName("Создание пользователя при заполнении всех обязательных полей")
    @Description("Успешное создание пользователя возвращает код 200")
    public void testCreateUserSuccess() {
        createResponse
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых пользователей")
    @Description("Создание второго пользователя с одними данными возвращает код ошибки 403")
    public void testCreateDuplicateUser() {
        Response duplicateUser = createUser(user);
        duplicateUser
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без почты")
    @Description("Создание пользователя без почты выдает ошибку 403")
    public void createUserWithoutEmailTest(){
        user.setEmail(null);
        createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Создание пользователя без пароля выдает ошибку 403")
    public void createUserWithoutPasswordTest(){
        user.setPassword(null);
        createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Создание пользователя без имени выдает ошибку 403")
    public void createUserWithoutNameTest(){
        user.setName(null);
        createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя с пустыми полями почты, пароля и имя")
    @Description("Создание пользователя с пустыми полями почты, пароля и имя выдает ошибку 403")
    public void createUserWithoutPasswordEmailAndNameTest(){
        user.setPassword(null);
        user.setEmail(null);
        user.setName(null);
        createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя с существующей почтой")
    @Description("Создание пользователя с уже существующей почтой возвращает код ошибки 403")
    public void testCreateCourierWithExistingEmail() {
        UserModel courierWithExistingEmail =
                new UserModel(user.getEmail(), PASSWORD, NAME);
        Response existingEmailResponse = createUser(courierWithExistingEmail);
        existingEmailResponse
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message",
                        equalTo("User already exists"));
    }
}