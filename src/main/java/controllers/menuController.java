package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class menuController implements Initializable {

    @FXML
    MenuBar mainMenuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
    }

    public void addShop(ActionEvent actionEvent) {
        try {
            Parent addChildView = FXMLLoader.load(getClass().getClassLoader().getResource("view/addShop.fxml"));
            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)mainMenuBar.getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addCashier(ActionEvent actionEvent) {
        try {
            Parent addChildView = FXMLLoader.load(getClass().getClassLoader().getResource("view/addCashier.fxml"));
            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)mainMenuBar.getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addProcuct(ActionEvent actionEvent) {
        try {
            Parent addChildView = FXMLLoader.load(getClass().getClassLoader().getResource("view/addProduct.fxml"));
            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)mainMenuBar.getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void shopping(ActionEvent actionEvent) {
        try {
            Parent addChildView = FXMLLoader.load(getClass().getClassLoader().getResource("view/shopping.fxml"));
            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)mainMenuBar.getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reference(ActionEvent actionEvent) {
        try {
            Parent addChildView = FXMLLoader.load(getClass().getClassLoader().getResource("view/getReference.fxml"));
            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)mainMenuBar.getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
