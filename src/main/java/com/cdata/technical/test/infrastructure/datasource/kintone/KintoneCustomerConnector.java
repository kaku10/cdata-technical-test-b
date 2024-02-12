package com.cdata.technical.test.infrastructure.datasource.kintone;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KintoneCustomerConnector {
    private static final String TABLE_NAME = "顧客管理（営業支援パック）";
    private final Connection kintoneConn;

    public KintoneCustomerConnector(String kintoneJdbcUrl) throws SQLException {
        this.kintoneConn = DriverManager.getConnection(kintoneJdbcUrl);
    }

    public List<HashMap<String, Object>> getCustomers() throws SQLException {
        return getRecords("SELECT * FROM \"" + TABLE_NAME + "\"");
    }

    public List<HashMap<String, Object>> getCustomersFromUpdatedAt(Timestamp updatedAt) throws SQLException {
        return getRecords("SELECT * FROM \"" + TABLE_NAME + "\" WHERE \"更新日時\" >= '" + updatedAt.toString() +"'");
    }

    public List<HashMap<String, Object>> getColumns() throws SQLException {
        return getRecords("SELECT ColumnName, DataTypeName FROM sys_tablecolumns WHERE TableName = '" + TABLE_NAME + "'");
    }

    private List<HashMap<String, Object>> getRecords(String sql) throws SQLException {
        Statement stat = kintoneConn.createStatement();
        boolean ret = stat.execute(sql);
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
}
