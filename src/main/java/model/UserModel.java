package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    private String email;
    private String password;
    private String name;

    public UserModel(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
