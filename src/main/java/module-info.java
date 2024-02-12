module com.cdata.technical.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.cdata.technical.test to javafx.fxml;
    exports com.cdata.technical.test;
    exports com.cdata.technical.test.presentation.controller;
    opens com.cdata.technical.test.presentation.controller to javafx.fxml;
}