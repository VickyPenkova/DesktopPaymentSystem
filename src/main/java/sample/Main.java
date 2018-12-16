package sample;

import entities.CashierEntity;
import entities.ProductEntity;
import entities.ShopEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.CashierService;
import services.ProductService;
import services.ShopService;

public class Main extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
      primaryStage.setScene(new Scene(root, 300, 275));
      primaryStage.show();
   }

   public static void main(String[] args) {
      ShopService shopService = new ShopService();
      CashierService cashierService = new CashierService();
      ProductService productService = new ProductService();

      // TODO: Make constructor to call this service sfter in the java fx application
      ShopEntity shop345 = shopService.addShop("345", "Mladost 4");

      CashierEntity cashierPetia = cashierService
            .addCashierInShop("Gery", "Petrova",
                  shopService.getShopById(6).getShopId());

//      ProductEntity apple = productService.addProductInShop("apple", 1.23,
      //            shopService.getShopById(6).getShopId());

      //org.hibernate.internal.ExceptionMapperStandardImpl mapManagedFlushFailure
      //ERROR: HHH000346: Error during managed flush
      //[Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)
      //Entity manager is not thread safe
      //TODO: Find another way to use  EntityManager- https://www.objectdb.com/java/jpa/persistence/overview
      ProductEntity cola = productService.addProductInShop("cola", 1.33,
            shopService.getShopById(2).getShopId());

      productService.registerProductWhenSelling(cola);

      launch(args);
   }

}
