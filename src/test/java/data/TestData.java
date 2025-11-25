package data;

import com.github.javafaker.Faker;

import java.util.List;

public class TestData {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru/";

    static Faker user = new Faker();
    public static final String EMAIL = user.name().lastName()+user.number().digits(2)+"@yandex.ru";
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();

    public static final String INCORRECT_EMAIL = "Йорик";
    public static final String INCORRECT_PASSWORD = "12345";

    public static final List<String> INVALID_INGREDIENT_HASH =
            List.of("ASD0c5a71d1f82001bdaaa6f",
                    "fdsjtf7365fluygi654654r6",
                    "000000000000000000000000"
                    );
}
