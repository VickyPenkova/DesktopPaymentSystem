package services;

import entities.CashReceiptEntity;
import entities.CashierEntity;
import entities.ProductEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;

import java.util.Map;

import static util.HibernateUtil.getEntityManager;

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
         cashReceiptEntity
               .setAmount(this.totalPriceOfCashReceipt(productsForCashReceipt));
         System.out.println(cashReceiptEntity.products().toString());
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

   private double totalPriceOfCashReceipt(Map<ProductEntity, Integer> p) {
      double totalAmount = 0;
      for (Map.Entry<ProductEntity, Integer> entry : p.entrySet()) {
         totalAmount += entry.getKey().getPrice() * entry.getValue();
      }

      return totalAmount;
   }

}
