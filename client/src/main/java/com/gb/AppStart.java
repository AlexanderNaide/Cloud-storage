package com.gb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AppStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Cloud_window.fxml")));
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Cloud storage");
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
