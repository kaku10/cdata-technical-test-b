package com.cdata.technical.test.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TopViewController {
    @FXML
    private Label label;

    @FXML
    protected void onReplicateButtonClick() {
        label.setText("Welcome to JavaFX Application!");
    }
}