package com.cdata.technical.test.infrastructure.datasource.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteCustomerConnector {
    private final Connection sqliteConn;

    public SqliteCustomerConnector(String sqliteJdbcUrl) throws SQLException {
        this.sqliteConn = DriverManager.getConnection(sqliteJdbcUrl);
    }
}
