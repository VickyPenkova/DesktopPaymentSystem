package sample;

import entities.CashReceiptEntity;
import entities.CashierEntity;
import entities.ProductEntity;
import entities.ShopEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.CashReceiptService;
import services.CashierService;
import services.ProductService;
import services.ShopService;

/**
 * Application starting point
 */
public class Main extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
      primaryStage.setScene(new Scene(root, 300, 275));
      primaryStage.show();
   }

   public static void main(String[] args) {
      /**
       * Add services for Application
       * ShopService, CashierService, ProductService, CashReceiptService
       */
      ShopService shopService = new ShopService();
      CashierService cashierService = new CashierService();
      ProductService productService = new ProductService();
      CashReceiptService cashReceiptService = new CashReceiptService();

      // Add Shop
      ShopEntity shop345 = shopService.addShop("345", "Mladost 4");

      // Add Cashier
      CashierEntity cashierGery = cashierService
            .addCashierInShop("Gery", "Petrova", shop345.getShopId());

      // Add product in Shop
      ProductEntity bread = productService
            .addProductInShop("bread", 1.23, shop345, 7, 1);

      ProductEntity apple = productService
            .addProductInShop("apple", 1, shop345, 7, 2);

      ProductEntity banana = productService
            .addProductInShop("banana", 0.76, shop345, 7, 3);

      // Check if product can be sold
      boolean toSellBanana = productService
            .markProductForSell(banana, 3, shop345);

      boolean toSellApple = productService
            .markProductForSell(apple, 2, shop345);

      System.out.println(
            "Products to be sold: " + productService.getProductsToBeSold());

      if (toSellBanana && toSellApple) {
         CashReceiptEntity receipt = cashReceiptService
               .generateCashReceipt(shop345, cashierGery,
                     productService.getProductsToBeSold());

         System.out.println("Receipt: " + receipt);
         System.out.println("All products in cash receipt" + cashReceiptService
               .getAllProductsInReceipt(receipt));

         System.out.println("Now writing cash receipt to file ...");
         cashReceiptService.writeCashReceiptToFile(receipt);
         System.out.println("Files are save in the parent directory!");
         System.out.println("Now deleting products ...");
         productService.deleteAllProductsMarkedForSell();

      }

      System.out.println(shopService.getAllShops());

      launch(args);
   }

}
