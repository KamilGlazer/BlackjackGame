package com.kamilglazer.blackjack;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Blackjack");
        stage.setResizable(false);

        String css = Objects.requireNonNull(Main.class.getResource("/com/kamilglazer/blackjack/styles.css")).toExternalForm();

        if(css == null){
            throw new RuntimeException("Nie znaleziono pliku css!");
        }

        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}