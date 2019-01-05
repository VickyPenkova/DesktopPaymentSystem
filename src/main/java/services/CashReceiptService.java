package services;

import entities.CashReceiptEntity;
import entities.CashierEntity;
import entities.ProductEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.HibernateUtil.getEntityManager;

/**
 * CashReceiptService class
 * Used to access CashReceiptEntities and talk to the database
 * Functionality:
 * 1. public generateCashReceipt
 * 2. public  writeCashReceiptToFile
 * 3. private totalPriceOfCashReceipt
 */
public class CashReceiptService {

   public CashReceiptEntity generateCashReceipt(ShopEntity shop,
         CashierEntity cashier,
         Map<ProductEntity, Integer> productsForCashReceipt) {
      EntityManager entityMgr = getEntityManager();
      CashReceiptEntity cashReceiptEntity = new CashReceiptEntity();
      cashReceiptEntity.initializeProducts(productsForCashReceipt);

      try {
         entityMgr.getTransaction().begin();

         cashReceiptEntity.setCashierId(cashier.getCashierId());
         cashReceiptEntity.setShopId(shop.getShopId());
         cashReceiptEntity.setShopEntity(shop);
         cashReceiptEntity.setTotalPrice(
               this.totalPriceOfCashReceipt(productsForCashReceipt));
         cashReceiptEntity.initializeCashierEntity(cashier);

         entityMgr.persist(cashReceiptEntity);

         entityMgr.getTransaction().commit();

      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

         System.out.println("Record Successfully Inserted In The Database");
      }
      System.out.println("Products to be sold are put into the cash receipt!");

      return cashReceiptEntity;
   }

   public Map<ProductEntity, Integer> getAllProductsInReceipt(
         CashReceiptEntity receiptReference) {
      System.out.println(receiptReference.products());
      return receiptReference.products();
   }

   public void writeCashReceiptToFile(CashReceiptEntity cashReceipt) {
      String fileName = cashReceipt.getCashReceiptId() + ".txt";
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

         String content = cashReceipt.toString();

         bw.write(content);

         System.out.println("Done");

      } catch (IOException e) {

         e.printStackTrace();

      }
   }

   public int getCountOfGeneratedCashReceipts() {
      EntityManager entityMgr = getEntityManager();
      List<CashReceiptEntity> listCashReceipt = entityMgr
            .createQuery("from CashReceiptEntity ", CashReceiptEntity.class)
            .getResultList();

      return listCashReceipt.size();
   }

   public int getCountOfGeneratedCashReceiptsIssuedFromCashier(int certainCashierId) {
      EntityManager entityMgr = getEntityManager();
      List<CashReceiptEntity> listCashReceipt = entityMgr
            .createQuery("from CashReceiptEntity ", CashReceiptEntity.class)
            .getResultList();
      int counter = 0;
      for (CashReceiptEntity cashReceipt : listCashReceipt) {
         if(cashReceipt.getCashierId() == certainCashierId){
            counter++;
         }
      }

      return counter;
   }

   public double getTotalPriceOfAllCashReceipts() {
      EntityManager entityMgr = getEntityManager();
      List<CashReceiptEntity> listCashReceipt = entityMgr
            .createQuery("from CashReceiptEntity ", CashReceiptEntity.class)
            .getResultList();
      double total = 0;
      for (CashReceiptEntity cashReceipt : listCashReceipt) {
         total += cashReceipt.getTotalPrice();
      }
      return total;
   }

   private double totalPriceOfCashReceipt(Map<ProductEntity, Integer> p) {
      double totalAmount = 0;
      for (Map.Entry<ProductEntity, Integer> entry : p.entrySet()) {
         totalAmount += entry.getKey().getPrice() * entry.getValue();
      }

      return totalAmount;
   }
}
