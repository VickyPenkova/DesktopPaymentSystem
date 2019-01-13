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
import services.ProductService;
import services.ShopService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addProductController implements Initializable {

    @FXML
    ComboBox shops;
    @FXML
    TextField name;
    @FXML
    TextField id;
    @FXML
    TextField quantity;
    @FXML
    TextField price;
    @FXML
    Button btnSave;
    @FXML
    Text message;


    public void saveProduct(ActionEvent actionEvent) {

        if(name.getText().isEmpty()){
            message.setText("Моля въведете име на продукта!");
        }
        else if(id.getText().isEmpty()){
            message.setText("Моля въведете ID на продукта!");
        }
        else if (Integer.parseInt(id.getText())<=0){
            message.setText("Моля въведете ID на продукта по-голямо от 0!");
        }
        else if(quantity.getText().isEmpty()){
            message.setText("Моля въведете количество на продукта!");
        }
        else if(Integer.parseInt(quantity.getText())<=0){
            message.setText("Моля въведете количество на продукта по-голямо от 0!");
        }
        else if(price.getText().isEmpty()){
            message.setText("Моля въведете цена на продукта!");
        }
        else if(Double.parseDouble(price.getText())<=0){
            message.setText("Моля въведете цена на  по-голяма от 0!");

        }
        else{
            ProductService productService = new ProductService();
            if(!productService.ifProductExists(Integer.parseInt(id.getText()))){
                message.setText("Продуктът с това ID същестува!");
            }

            else if(productService.ifProductExistsInShop(Integer.parseInt(id.getText()),
                    ((ShopEntity)shops.getSelectionModel().getSelectedItem()).getShopId())){

                message.setText("Продуктът с това ID същестува в този магазин!");

            }
            else{

                productService.addProductInShop(name.getText(),
                        Double.parseDouble(price.getText()),
                        ((ShopEntity)shops.getSelectionModel().getSelectedItem()),
                        Integer.parseInt(quantity.getText()),
                        Integer.parseInt(id.getText()));



                name.setText("");
                price.setText("");
                quantity.setText("");
                id.setText("");
                message.setText("Продуктът е записан в базата от данни!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        shops.getItems().clear();
        ShopService shopService = new ShopService();
        ArrayList<ShopEntity> shopsList = shopService.getAllShops();

        if (shopsList.isEmpty()) {
            name.setDisable(true);
            id.setDisable(true);
            quantity.setDisable(true);
            price.setDisable(true);
            btnSave.setDisable(true);
            message.setText("Няма нито един магазин в базата, моля създайте поне един!");

        } else {
            ObservableList<ShopEntity> observableList = FXCollections.observableList(shopsList);


            shops.setCellFactory(new Callback<ListView<ShopEntity>, ListCell<ShopEntity>>() {

                @Override
                public ListCell<ShopEntity> call(ListView<ShopEntity> p) {
                    final ListCell<ShopEntity> cell = new ListCell<ShopEntity>() {
                        @Override
                        protected void updateItem(ShopEntity t, boolean bln) {
                            super.updateItem(t, bln);

                            if (t != null) {
                                setText(t.getName() + " " + t.getAddress());
                            } else {
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