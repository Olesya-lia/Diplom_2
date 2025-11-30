import base.BaseUserTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;


public class CreateLoginTest extends BaseUserTest {

    @Test
    @DisplayName("Авторизация пользователя при заполнении всех обязательных полей")
    @Description("Успешная авторизация курьера возвращает код 200")
    public void testLoginSuccess() {
        loginUser(user)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация без email")
    @Description("Возвращает код ошибки 401")
    public void testLoginWithoutEmail() {
        user.setEmail(null);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Возвращает код ошибки 401")
    public void testLoginWithoutPassword(){
        user.setPassword(null);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация курьера без email и пароля")
    @Description("Авторизация курьера без email и пароля выдает ошибку 401")
    public void testLoginWithoutPasswordAndEmail(){
        user.setPassword(null);
        user.setEmail(null);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Авторизация c неверным email")
    @Description("Возвращает код ошибки 401")
    public void testLoginWithIncorrectEmail() {
        user.setEmail(INCORRECT_EMAIL);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    @Description("Возвращает код ошибки 404")
    public void testLoginWithIncorrectPassword() {
        user.setPassword(INCORRECT_PASSWORD);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация c неверным email и паролем")
    @Description("Авторизация с неверным email и паролем выдает ошибку 404")
    public void testLoginWithIncorrectPasswordAndEmail (){
        user.setPassword(INCORRECT_PASSWORD);
        user.setEmail(INCORRECT_EMAIL);
        loginUser(user)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message",
                        equalTo("email or password are incorrect"));
    }
}