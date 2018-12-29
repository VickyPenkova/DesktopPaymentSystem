package services;

import entities.ProductEntity;
import entities.ShopEntity;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.HibernateUtil.getEntityManager;

public class ProductService {
   private CashReceiptService cashReceiptService = new CashReceiptService();
   private ShopService shopService = new ShopService();
   private Map<ProductEntity, Integer> productsForCashReceipt = new HashMap<>();
   private List<ProductEntity> productsInShop = new ArrayList<>();

   public Map<ProductEntity, Integer> getProductsToBeSold() {
      return this.productsForCashReceipt;
   }

   private List<ProductEntity> getProductsInShop() {
      return productsInShop;
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
    * @param productToBeSold
    * @param count           - the number of productsForCashReceipt to be sold
    * @param shopReference            - the shop that is selling
    * @return - true or false is the product can be sold
    */

   public boolean markProductForSell(ProductEntity productToBeSold, int count,
         ShopEntity shopReference) {
      boolean toSell = false;

      EntityManager entityMgr = getEntityManager();
      ShopEntity shop= entityMgr
            .find(ShopEntity.class, shopReference.getShopId());

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
                  int newAmount = productToBeSold.getAmount();
                  productToBeSold.setAmount(newAmount + count);
                  markerForAdd = true;
               }
            }
            if (!markerForAdd) {
               productsForCashReceipt.put(productToBeSold, count);
            }

            // Think for another cases!

            toSell = true;
         }
      }

      return toSell;
   }

   private void deleteProduct(ProductEntity productToDelete, int amount) {
      EntityManager entityMgr = getEntityManager();
      try {
         ProductEntity toReduce = entityMgr
               .find(ProductEntity.class, productToDelete.getProductId());
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
      } finally {
         if (entityMgr.getTransaction().isActive())
            entityMgr.getTransaction().rollback();

      }
   }

   public void deleteAllProductsMarkedForSell() {
      for (Map.Entry<ProductEntity, Integer> entry : productsForCashReceipt
            .entrySet()) {
         deleteProduct(entry.getKey(), entry.getValue());
      }
   }

//   public ProductEntity getProductById(int productId) {
//      EntityManager entityMgr = getEntityManager();
//
//      try {
//
//         entityMgr.getTransaction().begin();
//         productEntity = entityMgr.find(ProductEntity.class, productId);
//
//         entityMgr.getTransaction().commit();
//      } finally {
//         if (entityMgr.getTransaction().isActive())
//            entityMgr.getTransaction().rollback();
//      }
//      return productEntity;
//   }

}
