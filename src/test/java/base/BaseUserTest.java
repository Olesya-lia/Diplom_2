package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.UserModel;
import org.junit.After;
import org.junit.Before;

import static data.TestData.*;
import static steps.UserSteps.*;

public class BaseUserTest {

    public UserModel user;
    public Response createResponse;
    public String tokenUser;


    @Before
    public void setup(){
        RestAssured.baseURI = BASE_URL;
        user = new UserModel(EMAIL, PASSWORD, NAME);
        createResponse = createUser(user);
        tokenUser = createResponse.jsonPath().getString("accessToken");
    }

    @After
    public void cleanUp() {
        if (tokenUser != null) {
            deleteUser(tokenUser);
        }
    }
}
