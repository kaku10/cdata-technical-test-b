package com.cdata.technical.test.infrastructure.datasource.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqliteCustomerConnector {
    private final Connection sqliteConn;

    public SqliteCustomerConnector(String sqliteJdbcUrl) throws SQLException {
        this.sqliteConn = DriverManager.getConnection(sqliteJdbcUrl);
    }

    public void createTableIfNotExists(String tableName, List<HashMap<String, Object>> columnsList) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (HashMap<String, Object> columns : columnsList) {
            sql.append("\"").append(columns.get("ColumnName")).append("\"")
                    .append(" ").append(columns.get("DataTypeName")).append(" ")
                    .append((Boolean) columns.get("IsKey") ? "PRIMARY KEY" : "")
                    .append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
        sqliteConn.createStatement().execute(sql.toString());
    }

    public boolean isTableNotExists(String tableName) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        try {
            stat.execute("SELECT * FROM " + tableName);
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public Timestamp getLastUpdatedTime(String tableName) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        boolean ret = stat.execute("SELECT MAX(更新日時) AS updatedAt FROM " + tableName + " GROUP BY AppId");
        if (ret) {
            ResultSet rs = stat.getResultSet();
            if (rs.next()) {
                return rs.getTimestamp("updatedAt");
            }
        }
        return null;
    }

    public int getCount(String tableName) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        boolean ret = stat.execute("SELECT COUNT(更新日時) AS cnt FROM " + tableName + " GROUP BY AppId");
        if (ret) {
            ResultSet rs = stat.getResultSet();
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    public void insertCustomers(String tableName, List<HashMap<String, Object>> customers) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        for (HashMap<String, Object> customer : customers) {
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            for (String key : customer.keySet()) {
                sql.append("\"").append(key).append("\",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ") VALUES (");
            for (Object value : customer.values()) {
                if (value == null) {
                    sql.append("'',");
                    continue;
                }
                sql.append("'").append(value).append("',");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            stat.execute(sql.toString());
        }
    }

    public void upsertCustomers(String tableName, List<HashMap<String, Object>> customers) throws SQLException {
        Statement stat = sqliteConn.createStatement();

        for (HashMap<String, Object> customer : customers) {
            StringBuilder sql = new StringBuilder("REPLACE INTO " + tableName + " (");
            for (String key : customer.keySet()) {
                sql.append("\"").append(key).append("\",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ") VALUES (");
            for (Object value : customer.values()) {
                if (value == null) {
                    sql.append("'',");
                    continue;
                }
                sql.append("'").append(value).append("',");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ")");
            stat.execute(sql.toString());
        }
    }

    public List<HashMap<String, Object>> getCustomers(String tableName) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        boolean ret = stat.execute("SELECT * FROM " + tableName);
        if (ret) {
            ArrayList<HashMap<String, Object>> records = new ArrayList<>();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                HashMap<String, Object> record = new HashMap<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    record.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
                records.add(record);
            }
            return records;
        }
        throw new SQLException("Failed to execute query");
    }


    public List<String> getHeader(String tableName) throws SQLException {
        Statement stat = sqliteConn.createStatement();
        boolean ret = stat.execute("PRAGMA table_info(" + tableName + ")");
        if (ret) {
            ArrayList<String> records = new ArrayList<>();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                records.add(rs.getString("name"));
            }
            return records;
        }
        throw new SQLException("Failed to execute query");
    }
}
