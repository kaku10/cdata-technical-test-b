package com.cdata.technical.test.domain.value;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;

public class CustomerTableData {
    private final HashMap<String, Object> row;

    public CustomerTableData(HashMap<String, Object> row) {
        this.row = row;
    }

    public StringProperty get(String key) {
        if(row.get(key) == null){
            return new SimpleStringProperty("");
        }
        return new SimpleStringProperty(row.get(key).toString());
    }
}
