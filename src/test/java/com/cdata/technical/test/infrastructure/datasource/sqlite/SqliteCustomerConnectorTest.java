package com.cdata.technical.test.infrastructure.datasource.sqlite;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class SqliteCustomerConnectorTest {

    @Test
    void createTableIfNotExists() throws SQLException {
        SqliteCustomerConnector customerConnector = new SqliteCustomerConnector(
                System.getenv("SQLITE_JDBC_URL")
        );
        customerConnector.createTableIfNotExists("test", new ArrayList<>(List.of("id", "name", "age")));
    }
}
