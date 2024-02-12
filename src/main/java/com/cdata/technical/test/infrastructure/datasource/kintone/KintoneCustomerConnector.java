package com.cdata.technical.test.infrastructure.datasource.kintone;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KintoneCustomerConnector {
    private final Connection kintoneConn;

    public KintoneCustomerConnector(String kintoneJdbcUrl) throws SQLException {
        this.kintoneConn = DriverManager.getConnection(kintoneJdbcUrl);
    }

    public List<HashMap<String, Object>> getCustomers() throws SQLException {
        return getRecords("SELECT * FROM \"顧客管理（営業支援パック）\"");
    }

    public List<HashMap<String, Object>> getColumns() throws SQLException {
        return getRecords("SELECT * FROM sys_tablecolumns WHERE TableName = '顧客管理（営業支援パック）'");
    }

    private List<HashMap<String, Object>> getRecords(String sql) throws SQLException {
        Statement stat = kintoneConn.createStatement();
        boolean ret = stat.execute(sql);
        if (ret) {
            ArrayList<HashMap<String, Object>> records = new ArrayList<>();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    int columnIndex = i;
                    records.add(new HashMap<>() {{
                        put(rs.getMetaData().getColumnLabel(columnIndex), rs.getObject(columnIndex));
                    }});
                }
            }
            return records;
        }
        throw new SQLException("Failed to execute query");
    }
}
