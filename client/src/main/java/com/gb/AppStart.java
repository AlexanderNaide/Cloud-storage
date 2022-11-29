package com.gb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class AppStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getResource("com\\gb\\Cloud_window.fxml"));
        loader.setLocation(this.getClass().getResource("Cloud_window.fxml"));
        Parent parent = loader.load();
//        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Cloud_window.fxml")));
//        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Cloud_window.fxml")));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("com/gb/style.css");
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cloud storage");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image(String.valueOf((getClass().getResource("cloud.png"))), 137, 84, false, false));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
