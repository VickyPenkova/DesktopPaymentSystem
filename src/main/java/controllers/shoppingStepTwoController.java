package controllers;

import entities.CashReceiptEntity;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class shoppingStepTwoController  implements Initializable {

    public void passReceipt(CashReceiptEntity entity){

        System.out.println(entity);

    }


    @Override
    public String toString() {
        return "shoppingStepTwoController{}";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




    }
}
