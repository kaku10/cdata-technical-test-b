package com.cdata.technical.test.infrastructure.datasource.kintone;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
}