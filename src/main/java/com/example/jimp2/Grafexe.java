package com.example.jimp2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;



public class Grafexe extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Grafexe.class.getResource("StartScreenView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 720);
        stage.setTitle("Grafexe");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/GrafexeLogo.png")).toString()));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}