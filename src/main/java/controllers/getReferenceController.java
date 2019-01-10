package controllers;

import entities.CashierEntity;
import entities.ShopEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import services.CashReceiptService;
import services.ShopService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class getReferenceController implements Initializable {

    @FXML
    ComboBox ask;
    @FXML
    ComboBox stores;
    @FXML
    ComboBox cashiers;
    @FXML
    Text answer;


    public void getReceiptCount(ActionEvent actionEvent){
        try {
            CashReceiptService cashReceiptService = new CashReceiptService();
            answer.setText("Обш брой бележки, издадени от касиер: "
                    + cashReceiptService.getCountOfGeneratedCashReceiptsIssuedFromCashier(((CashierEntity) cashiers.getSelectionModel().getSelectedItem()).getCashierId()));
        }
        catch(Exception e){}
    }
    public void somethingIsSelected(ActionEvent actionEvent) {
        answer.setText("");
        cashiers.setVisible(true);
        cashiers.getSelectionModel().clearSelection();
        cashiers.getItems().clear();
        ShopService shopService = new ShopService();
        List<CashierEntity> cashiersList = shopService.getCashiers(((ShopEntity) stores.getSelectionModel().getSelectedItem()).getShopId());

        ObservableList<CashierEntity> observableList = FXCollections.observableList(cashiersList);

        cashiers.getItems().clear();
        cashiers.setCellFactory(new Callback<ListView<CashierEntity>, ListCell<CashierEntity>>() {

            @Override
            public ListCell<CashierEntity> call(ListView<CashierEntity> p) {
                final ListCell<CashierEntity> listcell = new ListCell<CashierEntity>() {
                    @Override
                    protected void updateItem(CashierEntity t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getFirstName() + " " + t.getLastName());
                        } else {
                            setText(null);
                        }
                    }

                };

                return listcell;
            }

        });
        cashiers.getItems().setAll(observableList);

    }


    public void getReference(ActionEvent actionEvent){
        CashReceiptService cashReceiptService = new CashReceiptService();

        switch (ask.getSelectionModel().getSelectedIndex()){


            case 0:
                answer.setText("Общият брой издадени бележки е "+ cashReceiptService.getCountOfGeneratedCashReceipts());
                cashiers.setVisible(false);
                stores.setVisible(false);
                break;
            case 1:
                answer.setText("Общата сума на издадените бележки е "+cashReceiptService.getTotalPriceOfAllCashReceipts());
                cashiers.setVisible(false);
                stores.setVisible(false);
                break;
            case 2:
                stores.setVisible(true);




                break;



        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cashiers.setVisible(false);
        stores.setVisible(false);

        stores.getItems().clear();
        ShopService shopService = new ShopService();
        ArrayList<ShopEntity> shopsList = shopService.getAllShops();

        if (shopsList.isEmpty()) {
            answer.setText("Няма нито един магазин в базата, моля създайте поне един!");

        } else {
            ObservableList<ShopEntity> observableList = FXCollections.observableList(shopsList);


            stores.setCellFactory(new Callback<ListView<ShopEntity>, ListCell<ShopEntity>>() {

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
            stores.getItems().setAll(observableList);

        }
        ask.getItems().clear();
        ask.getItems().addAll("Общ брой издадени бележки","Обща сума на издадени бележки","Брой бележки, издадени на касиер");
    }
}
