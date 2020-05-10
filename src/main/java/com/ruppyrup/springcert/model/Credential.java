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
@Table(name = "${mysql.database.name}")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String credentialId;

    @Column
    private String url;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String user;

    public Credential(String credentialId, String url, String login, String password, String user) {
        this.credentialId = credentialId;
        this.url = url;
        this.login = login;
        this.password = password;
        this.user = user;
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
