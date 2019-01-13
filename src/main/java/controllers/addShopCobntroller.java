package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ShopService;


import java.net.URL;
import java.util.ResourceBundle;

//shit happened
//it's too late now
public class addShopCobntroller implements Initializable {


    @FXML
    TextField name;
    @FXML TextField address;
    @FXML
    Button btnSave;
    @FXML
    Text message;


    public void saveShop(ActionEvent event){

        if(name.getText().isEmpty()){
            message.setText("Моля въведете име на магазина!");
        }
        else if(address.getText().isEmpty()){
            message.setText("Моля въведете адрес на магазина!");
        }
        else{

            ShopService shopService = new ShopService();
            shopService.addShop(name.getText(),address.getText());
            name.setText("");
            address.setText("");
            message.setText("Магазинът е записан в базата от данни!");

        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
