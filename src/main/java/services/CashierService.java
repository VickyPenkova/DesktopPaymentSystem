package services;

import entities.CashierEntity;

import javax.persistence.EntityManager;

import static util.HibernateUtil.getEntityManager;

/**
 * CashierService class
 * Used to access CashierEntities and talk to the database
 * Functionality:
 * 1. public addCashierInShop
 */
public class CashierService {
   EntityManager entityMgr = getEntityManager();
   ShopService shopService = new ShopService();

   public CashierEntity addCashierInShop(String firstName, String lastName,
         int shopId) {
      CashierEntity cashierEntity = new CashierEntity();
      try {

         entityMgr.getTransaction().begin();

         // Operations that modify the database should come here.
         cashierEntity.setFirstName(firstName);
         cashierEntity.setLastName(lastName);
         cashierEntity.setShopId(shopId);
         cashierEntity.setShopEntity(shopService.getShopById(shopId));

         // Save record to Database
         entityMgr.persist(cashierEntity);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

         System.out.println("Record Successfully Inserted In The Database");
      }
      // add cashier to shop entity list -> test it
      shopService.addCashier(cashierEntity, shopService.getShopById(shopId));

      return cashierEntity;
   }
}
