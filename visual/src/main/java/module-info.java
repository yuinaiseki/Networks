module com.pdgvisual {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pdgvisual to javafx.fxml;
    exports com.pdgvisual;
}
