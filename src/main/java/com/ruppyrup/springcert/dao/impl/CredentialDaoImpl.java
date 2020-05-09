package com.ruppyrup.springcert.dao.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.model.Credential;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class CredentialDaoImpl extends JdbcDaoSupport implements CredentialDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${mysql.database.name}")
    private String dbname;


    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<Credential> getAllCredentials(String user) {
        String sql = "SELECT * FROM " + dbname + " WHERE user='" + user + "'";
        return getJdbcTemplate().query(sql, this::rowMapper);
    }

    @Override
    public Credential getCredential(String credentialId, String user) {
        String sql = "SELECT * FROM " + dbname + " WHERE credentialId='" + credentialId + "' AND user='" + user + "'";

        List<Credential> credentials = getJdbcTemplate().query(sql, this::rowMapper);

        if (credentials.isEmpty()) return null;

        return credentials.get(0);
    }

    @Override
    public Credential create(Credential credential) {

        if (getCredential(credential.getCredentialId(), credential.getUser()) != null) {
            return null;
        }

        String sql = "INSERT INTO " + dbname + "(credentialId, url, login, password, user) VALUES(:credentialId, :url, :login, :password, :user)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("credentialId", credential.getCredentialId())
                .addValue("url", credential.getUrl())
                .addValue("login", credential.getLogin())
                .addValue("password", credential.getPassword())
                .addValue("user", credential.getUser());
        namedParameterJdbcTemplate.update(sql, parameterSource);
        return credential;
    }

    @Override
    public Credential delete(String credentialId, String user) {
        Credential credentialToDelete = getCredential(credentialId, user);

        if (credentialToDelete == null) {
            return null;
        }

        String sql = "DELETE FROM " + dbname + " WHERE credentialId=? AND user=?";

        int rowsAffected = getJdbcTemplate().update(sql, credentialId, user);
        if (rowsAffected != 1) return new Credential(credentialId + " More or Less than one record altered");
        return credentialToDelete;
    }

    @Override
    public Credential update(Credential credential) {
        if (getCredential(credential.getCredentialId(), credential.getUser()) == null) {
            return null;
        }

        String sql = "UPDATE " + dbname + " SET url = ?, login = ?, password = ? WHERE credentialId= ? AND user=?";
        getJdbcTemplate().update(sql, credential.getUrl(), credential.getLogin(), credential.getPassword(), credential.getCredentialId(), credential.getUser());
        return credential;

    }

    @SneakyThrows
    private Credential rowMapper(ResultSet resultSet, int rowNum) {
        return new Credential(
                resultSet.getString("credentialId"),
                resultSet.getString("url"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("user"));
    }

}
