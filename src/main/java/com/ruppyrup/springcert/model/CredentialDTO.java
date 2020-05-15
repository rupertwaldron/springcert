package com.ruppyrup.springcert.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@EqualsAndHashCode
public class CredentialDTO {

    @NotNull
    private String credentialName;
    @NotNull
    private String url;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public CredentialDTO() {
    }

    public CredentialDTO(String credentialName, String url, String login, String password) {
        this.credentialName = credentialName;
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public CredentialDTO(Credential credential) {
        this.credentialName = credential.getCredentialName();
        this.login = credential.getLogin();
        this.password = credential.getPassword();
        this.url = credential.getUrl();
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
