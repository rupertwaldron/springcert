package com.ruppyrup.springcert.dao.impl;

import com.ruppyrup.springcert.dao.CredentialDao;
import com.ruppyrup.springcert.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CredentialDaoImpl extends JdbcDaoSupport implements CredentialDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<Credential> getAllCredentials() {
        String sql = "SELECT * FROM credential";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List<Credential> result = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Credential credential = new Credential();
            credential.setCredentialId((String) row.get("credentialId"));
            credential.setUrl((String) row.get("url"));
            credential.setLogin((String) row.get("login"));
            credential.setPassword((String) row.get("password"));
            result.add(credential);
        }

        return result;
    }

    @Override
    public Credential getCredential(String credentialId) {
        String sql = "SELECT * FROM credential where credentialId='" + credentialId + "'";
        Map<String, Object> row = getJdbcTemplate().queryForMap(sql);
        Credential credential = new Credential();
        credential.setCredentialId((String) row.get("credentialId"));
        credential.setUrl((String) row.get("url"));
        credential.setLogin((String) row.get("login"));
        credential.setPassword((String) row.get("password"));
        return credential;
    }

    @Override
    public boolean create(Credential credential) {
        return false;
    }

    @Override
    public boolean delete(Credential credential) {
        return false;
    }

    @Override
    public boolean update(Credential credential) {
        return false;
    }
}
