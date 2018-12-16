package services;

import entities.ProductEntity;

import javax.persistence.EntityManager;

import static util.HibernateUtil.getEntityManager;

public class ProductService {
   EntityManager entityMgr = getEntityManager();
   ProductEntity productEntity = new ProductEntity();
   ShopService shopService = new ShopService();

   public ProductEntity addProductInShop(String name, double price, int shopId) {
      try {

         entityMgr.getTransaction().begin();

         // Operations that modify the database should come here.
         productEntity.setName(name);
         productEntity.setPrice(price);
         productEntity.setShopId(shopId);
         productEntity.setShopEntity(shopService.getShopById(shopId));

         // Save record to Database
         entityMgr.persist(productEntity);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

         System.out.println("Record Successfully Inserted In The Database");
      }
      return productEntity;
   }

   public void registerProductWhenSelling(ProductEntity productToBeSold){
      try {

         entityMgr.getTransaction().begin();

         // Operations that modify the database should come here.
         entityMgr.remove(productToBeSold);

         // Save record to Database
         entityMgr.persist(productEntity);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

         System.out.println("Record Successfully Removed In The Database");
      }
   }
}
