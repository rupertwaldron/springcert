package com.ruppyrup.springcert.model;

public class Credential {
    private String credentialId;
    private String url;
    private String login;
    private String password;

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credential [" +
                "credentialId='" + credentialId +
                ", url='" + url +
                ", login='" + login +
                ", password='" + password +
                "]";
    }
}
