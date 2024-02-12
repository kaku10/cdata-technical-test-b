package com.cdata.technical.test.infrastructure.datasource.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomerConnector {
    private final Connection sqliteConn;

    public CustomerConnector(String sqliteJdbcUrl) throws SQLException {
        this.sqliteConn = DriverManager.getConnection(sqliteJdbcUrl);
    }
}
