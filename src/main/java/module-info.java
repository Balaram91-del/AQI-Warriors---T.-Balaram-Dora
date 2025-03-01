module org.example.carbonfootprintcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires java.net.http;

    opens org.example.carbonfootprintcalculator to javafx.fxml;
    exports org.example.carbonfootprintcalculator;
}