package services;

import entities.CashierEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static util.HibernateUtil.getEntityManager;

/**
 * ShopService class
 * Used to access ShopEntities and talk to the database
 * Functionality:
 * 1. public getShopById
 * 2. addCashier
 * 3. addShop
 */
public class ShopService {
   /**
    * @param name
    * @param address
    * @return
    */
   public ShopEntity addShop(String name, String address) {
      EntityManager entityMgr = getEntityManager();
      ShopEntity sp = new ShopEntity();
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

   /**
    * @param shopId
    * @return
    */
   public ShopEntity getShopById(int shopId) {
      EntityManager entityMgr = getEntityManager();
      ShopEntity sp;
      try {

         entityMgr.getTransaction().begin();
         sp = entityMgr.find(ShopEntity.class, shopId);
         if (sp == null) {
            System.out.println("No such shop!");
         }
         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
      }
      return sp;
   }

   /**
    * @param cashier
    * @param shop
    */
   public void addCashier(CashierEntity cashier, ShopEntity shop) {
      ShopEntity sp = new ShopEntity();
      sp.addCashier(cashier, shop);
   }

   public List<CashierEntity> getCashiers(int givenID){
      EntityManager entityMgr = getEntityManager();
      List<CashierEntity> cashierEntityList;
      try {
         Query query = entityMgr.
                 createQuery("Select c from CashierEntity c where c.cashierId=?1");
         query.setParameter(1,givenID);

         cashierEntityList=(List<CashierEntity>)query.getResultList();
         System.out.println(cashierEntityList);

      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
      }
      return cashierEntityList;
   }

   public ArrayList<ShopEntity> getAllShops() {
      EntityManager entityMgr = getEntityManager();
      List<ShopEntity> listShops = entityMgr
            .createQuery("from ShopEntity", ShopEntity.class).getResultList();

      return new ArrayList<>(listShops);
   }
}
