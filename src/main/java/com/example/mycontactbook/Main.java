package com.example.mycontactbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ContactBookGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 415);
        stage.setTitle("MyContactBook");
        stage.getIcons().add(new Image("address-book.png"));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();

    }
}