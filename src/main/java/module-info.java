module com.kamilglazer.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens com.kamilglazer.blackjack to javafx.fxml;
    exports com.kamilglazer.blackjack;
}