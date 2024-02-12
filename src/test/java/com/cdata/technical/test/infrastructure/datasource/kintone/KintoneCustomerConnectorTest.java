package com.cdata.technical.test.infrastructure.datasource.kintone;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;

class KintoneCustomerConnectorTest {

    @Test
    void getCustomers() throws SQLException {
        KintoneCustomerConnector customerConnector = new KintoneCustomerConnector(
                System.getenv("KINTONE_JDBC_URL")
        );
        customerConnector.getCustomers();
    }

    @Test
    void getColumns() throws SQLException {
        KintoneCustomerConnector customerConnector = new KintoneCustomerConnector(
                System.getenv("KINTONE_JDBC_URL")
        );
        customerConnector.getColumns();
    }

    @Test
    void getCustomersFromUpdatedAt() throws SQLException {
        KintoneCustomerConnector customerConnector = new KintoneCustomerConnector(
                System.getenv("KINTONE_JDBC_URL")
        );
        customerConnector.getCustomersFromUpdatedAt(new Timestamp(1707717600000L));
    }
}