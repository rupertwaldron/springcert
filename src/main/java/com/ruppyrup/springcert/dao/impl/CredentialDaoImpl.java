package com.ruppyrup.springcert.dao.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.dao.CredentialRowMapper;
import com.ruppyrup.springcert.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
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
        return getJdbcTemplate().query(sql, new CredentialRowMapper());
    }

    @Override
    public List<Credential> getCredential(String credentialId) {
        String sql = "SELECT * FROM " + dbname + " where credentialId='" + credentialId + "'";
        return getJdbcTemplate().query(sql, new CredentialRowMapper());
    }

    @Override
    public Credential create(Credential credential) {
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
    public boolean delete(Credential credential) {
        return false;
    }

    @Override
    public boolean update(Credential credential) {
        return false;
    }

    //todo need to add a clean to drop the table
}
