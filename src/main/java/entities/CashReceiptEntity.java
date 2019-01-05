package entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JPA CashReceiptEntity class is a POJO class,
 * i.e. an ordinary Java class that is marked
 * (annotated) as having the ability to represent objects in the database.
 * The class has cashReceiptId, cashierId, shopId, totalPrice
 * taken from the database
 * Has relation with Shop table as many to one.
 */
@Entity
@Table(name = "CashReceipt", schema = "DesktopPaymentSystem")
public class CashReceiptEntity {
   private int cashReceiptId;
   private int cashierId;
   private int shopId;
   private double totalPrice;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "shopId")
   private ShopEntity shopEntity;

   @ElementCollection
   @MapKeyColumn(name = "count")
   @MapKeyClass(ProductEntity.class)
   private Map<ProductEntity, Integer> products = new HashMap<>();

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cashierId")
   private CashierEntity cashierEntity;

   @Id
   @Column(name = "cashReceiptId", nullable = false)
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   public int getCashReceiptId() {
      return cashReceiptId;
   }

   public void setCashReceiptId(int cashReceiptId) {
      this.cashReceiptId = cashReceiptId;
   }

   @Basic
   @Column(name = "cashierId", nullable = false)
   public int getCashierId() {
      return cashierId;
   }

   public void setCashierId(int cashierId) {
      this.cashierId = cashierId;
   }

   @Basic
   @Column(name = "totalPrice", nullable = false)
   public double getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(double amount) {
      this.totalPrice = amount;
   }

   public void initializeProducts(Map<ProductEntity, Integer> products) {
      this.products = products;
   }

   public void initializeCashierEntity(CashierEntity cashierEntity) {
      this.cashierEntity = cashierEntity;
   }

   public Map<ProductEntity, Integer> products() {
      return this.products;
   }

   @Basic
   @Column(name = "shopId", nullable = false, length = 600)
   public int getShopId() {
      return shopId;
   }

   public void setShopId(int shopId) {
      this.shopId = shopId;
   }

   public void setShopEntity(ShopEntity shopEntity) {
      this.shopEntity = shopEntity;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      CashReceiptEntity that = (CashReceiptEntity) o;
      return cashReceiptId == that.cashReceiptId && cashierId == that.cashierId
            && shopId == that.shopId
            && Double.compare(that.totalPrice, totalPrice) == 0 && Objects
            .equals(shopEntity, that.shopEntity) && Objects
            .equals(products, that.products) && Objects
            .equals(cashierEntity, that.cashierEntity);
   }

   @Override
   public String toString() {
      return "ID of cash receipt: " + this.getCashierId() + "\nID of Shop: "
            + this.getShopId() + "\nName of Shop: " + this.shopEntity.getName()
            + "\nAddress of Shop: " + this.shopEntity.getAddress()
            + "\nID of Cashier: " + this.getCashierId() + "\nName of Cashier: "
            + this.cashierEntity.getFirstName() + " " + this.cashierEntity
            .getLastName() + "\nTotal price: " + this.totalPrice;
   }

   @Override
   public int hashCode() {
      return Objects
            .hash(cashReceiptId, cashierId, shopId, totalPrice, shopEntity,
                  products, cashierEntity);
   }

}
