package entities;

import javax.persistence.*;

@Entity
@Table(name = "Product", schema = "DesktopPaymentSystem")
public class ProductEntity {
   private int productId;
   private String name;
   private double price;
   //private int shopId;
   private int amount;
   //   private CashReceiptEntity cashReceiptEntity;

   private ShopEntity shop;

   public ProductEntity(){

   }

   public ProductEntity(int productId, String name, double price, int amount,
         ShopEntity shop) {
      this.productId = productId;
      this.name = name;
      this.price = price;
      this.amount = amount;
      this.shop = shop;
   }

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "shopId")
   public ShopEntity getShop() {
      return shop;
   }

   public void setShop(ShopEntity shop) {
      this.shop = shop;
   }

   @Id
   @Column(name = "productId", nullable = false)
   public int getProductId() {
      return productId;
   }

   public void setProductId(int productId) {
      this.productId = productId;
   }

   @Basic
   @Column(name = "name", nullable = false, length = 600)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Basic
   @Column(name = "price", nullable = false, precision = 0)
   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   @Basic
   @Column(name = "amount", nullable = false)
   public int getAmount() {
      return amount;
   }

   /**
    * @param amountPerProduct - when not set the default value is 1
    */
   public void setAmount(int amountPerProduct) {
      if(amountPerProduct > 1){
         this.amount = amountPerProduct;
      }else{
         this.amount = 1;
      }
   }

//   public void setCashReceiptEntity(CashReceiptEntity cashRecipt) {
//      this.cashReceiptEntity = cashRecipt;
//   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      ProductEntity that = (ProductEntity) o;

      if (productId != that.productId)
         return false;
      if (Double.compare(that.price, price) != 0)
         return false;
      if (name != null ? !name.equals(that.name) : that.name != null)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result;
      long temp;
      result = productId;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      temp = Double.doubleToLongBits(price);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      return result;
   }
}
