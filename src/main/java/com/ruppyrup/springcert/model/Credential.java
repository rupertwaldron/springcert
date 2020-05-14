package com.ruppyrup.springcert.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uuid;

    @Column
    private String credentialName;

    @Column
    private String url;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String user;

    public Credential() {
    }

    public Credential(String credentialName, String url, String login, String password, String user) {
        this.uuid = UUID.randomUUID().toString();
        this.credentialName = credentialName;
        this.url = url;
        this.login = login;
        this.password = password;
        this.user = user;
    }

    public Credential(Long id, String uuid, String credentialName, String url, String login, String password, String user) {
        this.id = id;
        this.uuid = uuid;
        this.credentialName = credentialName;
        this.url = url;
        this.login = login;
        this.password = password;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (credentialName != null ? !credentialName.equals(that.credentialName) : that.credentialName != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (credentialName != null ? credentialName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
