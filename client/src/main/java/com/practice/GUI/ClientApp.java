package com.practice.GUI;

import com.practice.backend.BackendClientApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class ClientApp extends Application {
    public static void main(String[] args) {
        new BackendClientApp().startBackend();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginWindow.fxml"));
        loader.setResources(ResourceBundle.getBundle("messages", Locale.US));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        //primaryStage.setTitle("My Application Title");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
