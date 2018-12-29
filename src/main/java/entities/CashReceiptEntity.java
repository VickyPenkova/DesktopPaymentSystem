package entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "CashReceipt", schema = "DesktopPaymentSystem")
public class CashReceiptEntity {
   private int cashReceiptId;
   private int cashierId;
   private int shopId;
   private double totalAmount;

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
   public double getAmount() {
      return totalAmount;
   }

   public void setAmount(double amount) {
      this.totalAmount = amount;
   }

   public void initializeProducts(Map<ProductEntity, Integer> products) {
      this.products = products;
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
            && shopId == that.shopId && Double.compare(that.totalAmount, totalAmount) == 0
            && Objects.equals(shopEntity, that.shopEntity) && Objects
            .equals(products, that.products) && Objects
            .equals(cashierEntity, that.cashierEntity);
   }

   @Override
   public int hashCode() {
      return Objects.hash(cashReceiptId, cashierId, shopId, totalAmount, shopEntity,
            products, cashierEntity);
   }
}
