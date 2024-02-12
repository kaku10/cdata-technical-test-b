package com.cdata.technical.test.infrastructure.datasource.kintone;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConnectorTest {

    @Test
    void getCustomers() throws SQLException {
        CustomerConnector customerConnector = new CustomerConnector(
                System.getenv("SQLITE_JDBC_URL"),
                System.getenv("KINTONE_JDBC_URL")
        );
        customerConnector.getCustomers();
    }
}