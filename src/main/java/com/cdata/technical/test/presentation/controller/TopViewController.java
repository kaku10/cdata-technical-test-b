package com.cdata.technical.test.presentation.controller;

import com.cdata.technical.test.domain.repository.CustomerRepository;
import com.cdata.technical.test.domain.value.CustomerTableData;
import com.cdata.technical.test.infrastructure.datasource.kintone.KintoneCustomerConnector;
import com.cdata.technical.test.infrastructure.datasource.sqlite.SqliteCustomerConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.SQLException;

public class TopViewController implements Initializable {
    private CustomerRepository repository;
    @FXML
    private Label label;
    @FXML
    private TableView<CustomerTableData> sqliteCustomersTable;
    @FXML
    private TableView<CustomerTableData> kintoneCustomersTable;

    public TopViewController() throws SQLException {
        SqliteCustomerConnector sqliteCustomerConnector = new SqliteCustomerConnector(
                System.getenv("SQLITE_JDBC_URL")
        );
        KintoneCustomerConnector kintoneCustomerConnector = new KintoneCustomerConnector(
                System.getenv("KINTONE_JDBC_URL")
        );
        this.repository = new CustomerRepository(
                sqliteCustomerConnector, kintoneCustomerConnector
        );
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onReplicateButtonClick() throws SQLException {
        try {
            int processedCount = repository.replicateCustomer();
            label.setText("Replication completed successfully! Processed " + processedCount + " rows.");
        } catch (Exception e) {
            label.setText("Replication failed: " + e.getMessage());
        }
        refreshTable();
    }

    private void refreshTable() throws SQLException {
        if (repository.isTableExists()) {
            updateTable(this.sqliteCustomersTable, repository.getCustomerHeaderFromSqlite(), repository.getCustomersFromSqlite());
        }
        updateTable(this.kintoneCustomersTable, repository.getCustomerHeaderFromKintone(), repository.getCustomersFromKintone());
    }

    private void updateTable(TableView<CustomerTableData> tableView, List<String> header, List<HashMap<String, Object>> records) {
        for (String column : header) {
            TableColumn<CustomerTableData, String> col = new TableColumn<>(column);
            col.setCellValueFactory(p -> p.getValue().get(column));
            tableView.getColumns().add(col);
        }
        ObservableList<CustomerTableData> data = FXCollections.observableArrayList();
        for (HashMap<String, Object> record : records) {
            tableView.itemsProperty().setValue(data);
            tableView.setItems(data);
            data.addAll(new CustomerTableData(record));
        }
    }
}