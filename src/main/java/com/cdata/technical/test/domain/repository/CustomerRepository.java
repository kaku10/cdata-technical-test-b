package com.cdata.technical.test.domain.repository;

import com.cdata.technical.test.infrastructure.datasource.kintone.KintoneCustomerConnector;
import com.cdata.technical.test.infrastructure.datasource.sqlite.SqliteCustomerConnector;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class CustomerRepository {
    private final SqliteCustomerConnector sqliteCustomerConnector;
    private final KintoneCustomerConnector kintoneCustomerConnector;
    private final String tableName = "顧客管理（営業支援パック）";

    public CustomerRepository(
            SqliteCustomerConnector sqliteCustomerConnector,
            KintoneCustomerConnector kintoneCustomerConnector
    ) {
        this.sqliteCustomerConnector = sqliteCustomerConnector;
        this.kintoneCustomerConnector = kintoneCustomerConnector;
    }

    public List<HashMap<String, Object>> getCustomersFromSqlite() throws SQLException {
        return this.sqliteCustomerConnector.getCustomers(tableName);
    }

    public List<String> getCustomerHeaderFromSqlite() throws SQLException {
        return this.sqliteCustomerConnector.getHeader(tableName);
    }

    public List<HashMap<String, Object>> getCustomersFromKintone() throws SQLException {
        return this.kintoneCustomerConnector.getCustomers();
    }

    public List<String> getCustomerHeaderFromKintone() throws SQLException {
        return this.kintoneCustomerConnector.getHeader();
    }

    public Boolean isTableExists() throws SQLException {
        return !this.sqliteCustomerConnector.isTableNotExists(tableName);
    }

    /**
     * @return processed rows count
     */
    public Integer replicateCustomer() throws SQLException {
        if (this.sqliteCustomerConnector.isTableNotExists(tableName)) {
            List<HashMap<String, Object>> columns = this.kintoneCustomerConnector.getColumns();
            this.sqliteCustomerConnector.createTableIfNotExists(tableName, columns);
        }
        Timestamp lastUpdatedAt = this.sqliteCustomerConnector.getLastUpdatedTime(tableName);
        if (lastUpdatedAt == null) {
            this.sqliteCustomerConnector.insertCustomers(tableName, this.kintoneCustomerConnector.getCustomers());
        } else {
            this.sqliteCustomerConnector.upsertCustomers(tableName, this.kintoneCustomerConnector.getCustomersFromUpdatedAt(lastUpdatedAt));
        }
    }
}
