package controllers;

import entities.CashReceiptEntity;
import entities.ProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import services.CashReceiptService;
import services.CashierService;
import services.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class shoppingController implements Initializable {

    @FXML
    Button btnOK;
    @FXML
    Button generateReciept;
    @FXML
    Button btnAdd;
    @FXML
    TextField cashierID;
    @FXML Button btnNewSale;
    @FXML
    TextField productID;
    @FXML
    TextField productAmount;
    @FXML
    Text message;
    @FXML
    Text label1;
    @FXML
    Text label2;
    @FXML
    Text bigLabel;
    @FXML
    Text messageForSale;
    @FXML ListView cashRecieptList;
    @FXML Button btnPrint;


    ProductService productService;
    ProductEntity productEntity = new ProductEntity();
    CashierService cashierService = new CashierService();
    CashReceiptEntity entity = new CashReceiptEntity();
    CashReceiptService cashReceiptService = new CashReceiptService();

    public void printReciept(ActionEvent actionEvent){
        cashReceiptService.writeCashReceiptToFile(entity);
        productService.getProductsToBeSold().clear();
        btnNewSale.setDisable(false);
        btnPrint.setDisable(true);
    }

    public void backToStart(ActionEvent actionEvent){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/shopping.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnNewSale.getScene().setRoot(root);


    }
    public void issueReceipt(ActionEvent actionEvent){



        entity = cashReceiptService.generateCashReceipt(productEntity.getShop(),cashierService.getCashierById(Integer.parseInt(cashierID.getText())),productService.getProductsToBeSold());
        entity = cashReceiptService.generateCashReceipt(productEntity.getShop(),cashierService.getCashierById(Integer.parseInt(cashierID.getText())),productService.getProductsToBeSold());


        cashRecieptList.setVisible(true);
        btnNewSale.setVisible(true);
        btnNewSale.setDisable(true);
        btnPrint.setVisible(true);
        productID.setVisible(false);
        productAmount.setVisible(false);
        btnAdd.setVisible(false);
        generateReciept.setVisible(false);
        message.setVisible(false);
        messageForSale.setText("Общо: " + entity.getTotalPrice() + "лв.");
        btnOK.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        bigLabel.setText("Вашата касова бележка");

        List<ProductEntity> list = new ArrayList<>(entity.products().keySet());

        ObservableList<ProductEntity> observableList = FXCollections.observableList(list);


        cashRecieptList.setCellFactory(new Callback<ListView<ProductEntity>,ListCell<ProductEntity>>(){

            @Override
            public ListCell<ProductEntity> call(ListView<ProductEntity> p) {
                final ListCell<ProductEntity> cell = new ListCell<ProductEntity>(){
                    @Override
                    protected void updateItem(ProductEntity t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t.getName() + " " + entity.products().get(t)+"бр.");
                        }else{
                            setText(null);
                        }
                    }

                };

                return cell;
            }

        });
        cashRecieptList.getItems().setAll(observableList);
        productService.deleteAllProductsMarkedForSell();

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
                        for(Map.Entry<ProductEntity,Integer> entry : productService.getProductsToBeSold().entrySet()){
                            System.out.println(entry.getKey().getName() +" "+ entry.getValue());
                        }
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
        productService = new ProductService();

        productID.setDisable(true);
        productAmount.setDisable(true);
        btnAdd.setDisable(true);
        generateReciept.setDisable(true);
        cashRecieptList.setVisible(false);
        btnPrint.setVisible(false);
        btnNewSale.setVisible(false);
        cashRecieptList.getItems().clear();
    }
}
