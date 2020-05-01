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
    public List<Credential> getAllCredentials() {
        String sql = "SELECT * FROM " + dbname;
        return getJdbcTemplate().query(sql, this::rowMapper);
    }

    @Override
    public Credential getCredential(String credentialId) {
        String sql = "SELECT * FROM " + dbname + " where credentialId='" + credentialId + "'";

        List<Credential> credentials = getJdbcTemplate().query(sql, this::rowMapper);

        if (credentials.isEmpty()) return null;

        return credentials.get(0);
    }

    @Override
    public Credential create(Credential credential) {

        if (getCredential(credential.getCredentialId()) != null) {
            return new Credential(credential.getCredentialId() + " Already exists");
        }

        String sql = "INSERT INTO " + dbname + "(credentialId, url, login, password) VALUES(:credentialId, :url, :login, :password)";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("credentialId", credential.getCredentialId())
                .addValue("url", credential.getUrl())
                .addValue("login", credential.getLogin())
                .addValue("password", credential.getPassword());
        namedParameterJdbcTemplate.update(sql, parameterSource);
        return credential;
    }

    @Override
    public Credential delete(String credentialId) {
        Credential credentialToDelete = getCredential(credentialId);

        if (credentialToDelete == null) {
            return new Credential(credentialId + " Does not exist");
        }


        String sql = "DELETE FROM " + dbname + " WHERE credentialId=?";

        int rowsAffected = getJdbcTemplate().update(sql, credentialId);

        if (rowsAffected != 1) return new Credential(credentialId + " More or Less than one record altered");

        return credentialToDelete;
    }

    @Override
    public Credential update(Credential credential) {
        if (getCredential(credential.getCredentialId()) == null) {
            return new Credential(credential.getCredentialId() + " Does not exist");
        }

        String sql = "UPDATE " + dbname + " SET url = ?, login = ?, password = ? WHERE credentialId= ?";
        getJdbcTemplate().update(sql, credential.getUrl(), credential.getLogin(), credential.getPassword(), credential.getCredentialId());
        return credential;

    }

    @SneakyThrows
    private Credential rowMapper(ResultSet resultSet, int rowNum) {
        return new Credential(
                resultSet.getString("credentialId"),
                resultSet.getString("url"),
                resultSet.getString("login"),
                resultSet.getString("password"));
    }

}
