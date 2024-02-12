package com.cdata.technical.test.infrastructure.datasource.sqlite;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

class SqliteCustomerConnectorTest {
    @Test
    void getLastUpdatedTime() throws SQLException {
        SqliteCustomerConnector customerConnector = new SqliteCustomerConnector(
                System.getenv("SQLITE_JDBC_URL")
        );
        customerConnector.getLastUpdatedTime("顧客管理（営業支援パック）");
    }
}
