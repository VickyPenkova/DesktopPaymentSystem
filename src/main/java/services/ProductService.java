package services;

import entities.ProductEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static util.HibernateUtil.getEntityManager;

/**
 * ProductService class
 * Used to access ProductEntities and talk to the database
 * Functionality:
 * 1. public addProductInShop
 * 2. public markProductForSell
 * 3. private deleteProduct
 * 4. public deleteAllProductsMarkedForSell
 * 5. public getProductById
 */
public class ProductService {
   private Map<ProductEntity, Integer> productsForCashReceipt = new HashMap<>();

   public Map<ProductEntity, Integer> getProductsToBeSold() {
      return this.productsForCashReceipt;
   }

   /**
    * @param name   - the nbame of the product
    * @param price  - the price of the product
    * @param shop   - the shop associated with the product
    * @param amount - the quantity os the productsForCashReceipt
    * @return ProductEntity generated
    */
   public ProductEntity addProductInShop(String name, double price,
                                         ShopEntity shop, int amount, int productId) {
      EntityManager entityMgr = getEntityManager();
      ProductEntity productEntity = new ProductEntity();
      try {
         entityMgr.getTransaction().begin();

         // Operations that modify the database should come here.
         productEntity.setName(name);
         productEntity.setPrice(price);
         productEntity.setAmount(amount);
         productEntity.setProductId(productId);

         ShopEntity shopEntity = entityMgr
                 .find(ShopEntity.class, shop.getShopId());
         shopEntity.addProduct(productEntity);

         System.out.println(
                 "Size in addProductInShop: " + shopEntity.getProducts().size());
         System.out.println(shopEntity.getProducts());

         entityMgr.persist(productEntity);
         entityMgr.persist(shopEntity);

         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
         System.out.println("Record Successfully Inserted In The Database");
      }

      return productEntity;
   }

   /**
    * @param givenId
    * @return - true if the product exists
    */
   public boolean ifProductExists(int givenId){
      EntityManager entityMgr = getEntityManager();
      if(entityMgr.find(ProductEntity.class,givenId) == null)
         return true;
      else return false;
   }

   /**
    * @param productToBeSold
    * @param count           - the number of productsForCashReceipt to be sold
    * @param shopReference   - the shop that is selling
    * @return - true or false if the product can be sold
    */

   public void markProductForSell(ProductEntity productToBeSold, int count,
                                  ShopEntity shopReference) {
      EntityManager entityMgr = getEntityManager();
      ShopEntity shop = entityMgr
              .find(ShopEntity.class, shopReference.getShopId());
      entityMgr.getTransaction().begin();
      if(shop == null){
         System.out.println("No such shop!");
         return;
      }

      if (shop.getProducts().size() == 0) {
         System.out.println(
                 "Not enough products for CashReceipt in the shop " + shop
                         .getName());
      } else {
         if (count > productToBeSold.getAmount()) {
            System.out.println(
                    "Not enough productsForCashReceipt of this type in the shop "
                            + shop.getName());
         } else if (count <= productToBeSold.getAmount()) {
            // Check for the same products already scanned
            boolean markerForAdd = false;
            for (ProductEntity items : productsForCashReceipt.keySet()) {
               if (productToBeSold.equals(items)) {
                  int newAmount = productsForCashReceipt.get(items) + count;
                  productsForCashReceipt.put(items,newAmount);
                  markerForAdd = true;
               }
            }
            if (!markerForAdd) {
               productsForCashReceipt.put(productToBeSold, count);
            }
         }
      }
      if (entityMgr.getTransaction().isActive())
         entityMgr.getTransaction().rollback();
   }

   /**
    * @param productToDelete
    * @param amount
    */
   private void deleteProduct(ProductEntity productToDelete, int amount) {
      EntityManager entityMgr = getEntityManager();
      try {
         ProductEntity toReduce = entityMgr
                 .find(ProductEntity.class, productToDelete.getProductId());
         if (toReduce != null) {
            if (productToDelete.getAmount() > amount) {

               entityMgr.getTransaction().begin();
               toReduce.setAmount(toReduce.getAmount() - amount);
               entityMgr.getTransaction().commit();

               System.out.println(toReduce.getAmount());
            } else if (productToDelete.getAmount() == amount) {
               entityMgr.getTransaction().begin();
               entityMgr.remove(toReduce);
               entityMgr.getTransaction().commit();
            }
            System.out.println("Record Successfully Removed In The Database");
         }
         System.out.println("Cannot delete product!");

      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

      }
   }

   /**
    *
    */
   public void deleteAllProductsMarkedForSell() {
      for (Map.Entry<ProductEntity, Integer> entry : productsForCashReceipt
              .entrySet()) {
         deleteProduct(entry.getKey(), entry.getValue());
      }
   }

   /**
    * @param productId
    * @param shopId
    * @return boolean
    */
   public boolean ifProductExistsInShop(int productId, int shopId) {
      EntityManager entityMgr = getEntityManager();
      ShopEntity shop;
      try {

         entityMgr.getTransaction().begin();
         shop = entityMgr.find(ShopEntity.class, shopId);
         if (shop != null) {
            for (ProductEntity product : shop.getProducts()) {
               if (productId == product.getProductId()) {
                  return true;
               }
            }
         }
         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
      }

      return false;
   }

   public ProductEntity getProductById(int productId) {
      EntityManager entityMgr = getEntityManager();
      ProductEntity productEntity;
      try {
         entityMgr.getTransaction().begin();
         productEntity = entityMgr.find(ProductEntity.class, productId);
         entityMgr.getTransaction().commit();
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();
      }
      return productEntity;
   }
}
