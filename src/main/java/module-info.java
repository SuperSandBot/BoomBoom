module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens application to javafx.fxml;
    exports application;
}
