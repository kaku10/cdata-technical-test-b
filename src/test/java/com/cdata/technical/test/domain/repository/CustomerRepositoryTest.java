package com.cdata.technical.test.domain.repository;

import com.cdata.technical.test.infrastructure.datasource.kintone.KintoneCustomerConnector;
import com.cdata.technical.test.infrastructure.datasource.sqlite.SqliteCustomerConnector;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {
    @Test
    void replicateCustomer() throws SQLException {
        CustomerRepository customerRepository = new CustomerRepository(
                new SqliteCustomerConnector(
                        System.getenv("SQLITE_JDBC_URL")
                ),
                new KintoneCustomerConnector(
                        System.getenv("KINTONE_JDBC_URL")
                )
        );
        assertDoesNotThrow(customerRepository::replicateCustomer);
    }
}