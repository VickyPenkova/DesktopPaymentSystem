package controllers;

import entities.ShopEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import services.CashierService;
import services.ShopService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addCashierController implements Initializable {


    @FXML
    ComboBox shops;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    Button btnSave;
    @FXML
    Text message;


    public void saveCashier(ActionEvent action){

        if(firstName.getText().isEmpty()){
            message.setText("Моля въведете име на касиера!");
        }
        else if(lastName.getText().isEmpty()){
            message.setText("Моля въведете фамилия на касиера!");
        }
        else{

            CashierService cashierService = new CashierService();
            cashierService.addCashierInShop(firstName.getText(),lastName.getText(),((ShopEntity)shops.getSelectionModel().getSelectedItem()).getShopId());
            firstName.setText("");
            lastName.setText("");
            message.setText("Касиерът е записан в базата от данни!");

        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shops.getItems().clear();
        ShopService shopService = new ShopService();
        ArrayList<ShopEntity>shopsList=shopService.getAllShops();

        if(shopsList.isEmpty()) {
            firstName.setDisable(true);
            lastName.setDisable(true);
            btnSave.setDisable(true);
            message.setText("Няма нито един магазин в базата, моля създайте поне един!");

        }
        else{
            ObservableList<ShopEntity> observableList = FXCollections.observableList(shopsList);


            shops.setCellFactory(new Callback<ListView<ShopEntity>, ListCell<ShopEntity>>(){

                @Override
                public ListCell<ShopEntity> call(ListView<ShopEntity> p) {
                    final ListCell<ShopEntity> cell = new ListCell<ShopEntity>(){
                        @Override
                        protected void updateItem(ShopEntity t, boolean bln) {
                            super.updateItem(t, bln);

                            if(t != null){
                                setText(t.getName() +" " + t.getAddress());
                            }else{
                                setText(null);
                            }
                        }

                    };

                    return cell;
                }

            });
            shops.getItems().setAll(observableList);

        }

    }
}
