module com.example.mycontactbook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.mycontactbook to javafx.fxml;
    exports com.example.mycontactbook;
}