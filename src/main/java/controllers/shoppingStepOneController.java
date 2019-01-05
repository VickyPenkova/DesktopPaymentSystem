package controllers;

import entities.CashReceiptEntity;
import entities.CashierEntity;
import entities.ProductEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.CashReceiptService;
import services.CashierService;
import services.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class shoppingStepOneController implements Initializable {

    @FXML
    Button btnOK;
    @FXML
    Button generateReciept;
    @FXML
    Button btnAdd;
    @FXML
    TextField cashierID;
    @FXML
    TextField productID;
    @FXML
    TextField productAmount;
    @FXML
    Text message;
    @FXML
    Text messageForSale;


    ProductService productService = new ProductService();
    ProductEntity productEntity = new ProductEntity();
    CashierService cashierService = new CashierService();

    public void issueReceipt(ActionEvent actionEvent){


        CashReceiptService cashReceiptService = new CashReceiptService();
        CashReceiptEntity cashReceiptEntity = cashReceiptService.generateCashReceipt(productEntity.getShop(),cashierService.getCashierById(Integer.parseInt(cashierID.getText())),productService.getProductsToBeSold());

        System.out.println(cashReceiptEntity);

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent addChildView = loader.load(getClass().getClassLoader().getResource("view/shoppingStepTwo.fxml"));

            loader.load(getClass().getClassLoader().getResource("view/shoppingStepTwo.fxml"));
            shoppingStepTwoController stepTwoController = new shoppingStepTwoController();
            loader.setController(stepTwoController);
            stepTwoController = loader.getController();
            stepTwoController.passReceipt(cashReceiptEntity);

            Scene addChildScene = new Scene(addChildView);

            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(addChildScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void addProduct(ActionEvent actionEvent) {

        if (productID.getText().isEmpty()) {

            messageForSale.setText("Моля въведете ID на продукт!");
        } else if (productAmount.getText().isEmpty()) {

            messageForSale.setText("Моля въведете количество на продукт!");
        } else if (Integer.parseInt(productID.getText()) <= 0) {
            messageForSale.setText("Моля въведете ID на продукт > 0!");

        } else if (Integer.parseInt(productAmount.getText()) <= 0) {
            messageForSale.setText("Моля въведете количество на продукт >0!");

        } else {
            int givenAmount = Integer.parseInt(productAmount.getText());
            int givenID = Integer.parseInt(productID.getText());
            productEntity = productService.getProductById(givenID);
            if (productEntity != null) {
                if (!productService.ifProductExistsInShop(givenID, productEntity.getShop().getShopId())) {

                    messageForSale.setText("Няма продукт с такова ID в този магазин!");

                } else {
                    if (productEntity.getAmount() < givenAmount) {

                        messageForSale.setText("Няма толкова бройки от продукта в този магазин!Наличност: " + productEntity.getAmount());

                    } else {

                        productService.markProductForSell(productEntity, givenAmount, productEntity.getShop());
                        messageForSale.setText("Продуктът е успешно добавен в количката!");
                        generateReciept.setDisable(false);
                    }
                }

            } else {
                messageForSale.setText("Няма продукт с такова ID изобщо!");

            }
        }
    }

    public void validateCashier(ActionEvent actionEvent){

        if(cashierID.getText().isEmpty()){
            message.setText("Моля въведете касиерско ID!");
        }
        else if(Integer.parseInt(cashierID.getText())<=0){
            message.setText("Моля въведете касиерско ID по-голямо от 0!");
        }
        else{
            CashierService cashierService = new CashierService();

            if(!cashierService.ifCashierExists(Integer.parseInt(cashierID.getText()))){
                message.setText("Моля въведете валидно ID!");

            }
            else{
                cashierID.setDisable(true);
                btnOK.setDisable(true);
                message.setText("");
                productID.setDisable(false);
                productAmount.setDisable(false);
                btnAdd.setDisable(false);



            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productID.setDisable(true);
        productAmount.setDisable(true);
        btnAdd.setDisable(true);
        generateReciept.setDisable(true);
    }
}
