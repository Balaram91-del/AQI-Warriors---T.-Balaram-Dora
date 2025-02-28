module org.example.carbonfootprintcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.carbonfootprintcalculator to javafx.fxml;
    exports org.example.carbonfootprintcalculator;
}