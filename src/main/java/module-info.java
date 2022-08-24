module com.example.jimp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jimp2 to javafx.fxml;
    exports com.example.jimp2;
}