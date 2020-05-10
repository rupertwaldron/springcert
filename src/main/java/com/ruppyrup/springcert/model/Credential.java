package com.ruppyrup.springcert.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.ruppyrup.springcert.constants.Constants.hiddenParam;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Credential(String credentialName, String url, String login, String password, String user) {
        this.credentialName = credentialName;
        this.url = url;
        this.login = login;
        this.password = password;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "CredentialName='" + credentialName + '\'' +
                ", url='" + url + '\'' +
                ", login='" + hiddenParam + '\'' +
                ", password='" + hiddenParam + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
