package com.cdata.technical.test.infrastructure.datasource.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SqliteCustomerConnector {
    private final Connection sqliteConn;

    public SqliteCustomerConnector(String sqliteJdbcUrl) throws SQLException {
        this.sqliteConn = DriverManager.getConnection(sqliteJdbcUrl);
    }

    public void createTableIfNotExists(String tableName, List<String> columns) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (String column : columns) {
            sql += column + " TEXT,";
        }
        sql = sql.substring(0, sql.length() - 1) + ")";
        sqliteConn.createStatement().execute(sql);
    }
}
