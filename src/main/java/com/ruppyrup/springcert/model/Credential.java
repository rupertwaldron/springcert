package com.ruppyrup.springcert.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ruppyrup.springcert.constants.Constants.hiddenParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private String credentialId;
    private String url;
    private String login;
    private String password;
    private String user;

    public Credential(String credentialId) {
        this.credentialId = credentialId;
        this.url = hiddenParam;
        this.login = hiddenParam;
        this.password = hiddenParam;
        this.user = hiddenParam;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "credentialId='" + credentialId + '\'' +
                ", url='" + url + '\'' +
                ", login='" + hiddenParam + '\'' +
                ", password='" + hiddenParam + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
