package com.ruppyrup.springcert.dao;

import com.ruppyrup.springcert.model.Credential;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialRowMapper implements RowMapper<Credential> {
    @Override
    public Credential mapRow(ResultSet rs, int rowNum) throws SQLException {
        Credential credential = new Credential();
        credential.setCredentialId(rs.getString("credentialId"));
        credential.setUrl(rs.getString("url"));
        credential.setLogin(rs.getString("login"));
        credential.setPassword(rs.getString("password"));
        return credential;
    }
}
