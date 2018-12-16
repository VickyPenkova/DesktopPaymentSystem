package sample;

import entities.ShopEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;


import static util.HibernateUtil.getEntityManager;

public class Main extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
      primaryStage.setScene(new Scene(root, 300, 275));
      primaryStage.show();
   }

   public static void main(String[] args) {
      //test entity manager
      EntityManager entityMgr = getEntityManager();
      entityMgr.getTransaction().begin();

      ShopEntity sp = new ShopEntity();
      sp.setName("Shop1");
      sp.setAddress("AddressOfShop1");
      sp.setShopId(0);
      entityMgr.persist(sp);

      entityMgr.getTransaction().commit();

      entityMgr.clear();
      System.out.println("Record Successfully Inserted In The Database");

      launch(args);
   }




}
