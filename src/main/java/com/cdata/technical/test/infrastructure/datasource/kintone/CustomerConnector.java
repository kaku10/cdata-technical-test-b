package com.cdata.technical.test.infrastructure.datasource.kintone;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerConnector {
    private final Connection sqliteConn;
    private final Connection kintoneConn;

    public CustomerConnector(String sqliteJdbcUrl, String kintoneJdbcUrl) throws SQLException {
        this.sqliteConn = DriverManager.getConnection(sqliteJdbcUrl);
        this.kintoneConn = DriverManager.getConnection(kintoneJdbcUrl);
    }

    public List<HashMap<String, Object>> getCustomers() throws SQLException {
        Statement stat = kintoneConn.createStatement();
        boolean ret = stat.execute(
                "SELECT * FROM \"顧客管理（営業支援パック）\""
        );
        if (ret) {
            ArrayList<HashMap<String, Object>> customers = new ArrayList<>();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    int columnIndex = i;
                    customers.add(new HashMap<>() {{
                        put(rs.getMetaData().getColumnLabel(columnIndex), rs.getObject(columnIndex));
                    }});
                }
            }
            return customers;
        }
        throw new SQLException("Failed to execute query");
    }
}
