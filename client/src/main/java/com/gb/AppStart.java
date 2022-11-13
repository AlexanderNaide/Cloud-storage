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

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("com\\gb\\Cloud_window.fxml"));
//        Parent parent = loader.load();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Cloud_window.fxml")));
//        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Cloud_window.fxml")));
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Cloud storage");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
