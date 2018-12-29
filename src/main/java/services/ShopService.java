package services;

import entities.CashierEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;

import static util.HibernateUtil.getEntityManager;

public class ShopService {
   ShopEntity sp = new ShopEntity();

   public ShopEntity addShop(String name, String address) {
      EntityManager entityMgr = getEntityManager();

      try {

         entityMgr.getTransaction().begin();

         // Operations that modify the database should come here.
         sp.setName(name);
         sp.setAddress(address);

         // Save record to Database
         entityMgr.persist(sp);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

         System.out.println("Record Successfully Inserted In The Database");
      }
      return sp;
   }

   public ShopEntity getShopById(int shopId) {
      EntityManager entityMgr = getEntityManager();

      try {

         entityMgr.getTransaction().begin();
         sp = entityMgr.find(ShopEntity.class, shopId);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
      }
      return sp;
   }

   public void addCashier(CashierEntity cashier, ShopEntity shop) {
      sp.addCashier(cashier, shop);
   }
}
