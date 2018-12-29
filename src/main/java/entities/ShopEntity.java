package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Shop", schema = "DesktopPaymentSystem")
public class ShopEntity {
   private int shopId;
   private String name;
   private String address;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<CashierEntity> cashiers = new ArrayList<>();


   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<CashReceiptEntity> cashReceipts = new ArrayList<>();

   private List<ProductEntity> products = new ArrayList<>();

   @OneToMany(mappedBy = "shop",  cascade = CascadeType.ALL, orphanRemoval = true)
   public List<ProductEntity> getProducts() {
      return products;
   }

   public void setProducts(List<ProductEntity> products) {
      this.products = products;
   }

   public void addProduct(ProductEntity product){
      products.add(product);
      product.setShop(this);

      System.out.println(products.size());
   }

   public  void removeProduct(ProductEntity product){
      products.remove(product);
      product.setShop(null);
   }

   @Id
   @Column(name = "shopId", nullable = false)
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   public int getShopId() {
      return shopId;
   }

   public void setShopId(int shopId) {
      this.shopId = shopId;
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
   @Column(name = "address", nullable = false, length = -1)
   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   // Cashier is connected with the shop that works in
   public void addCashier(CashierEntity cashier, ShopEntity shop) {
      cashiers.add(cashier);
      cashier.setShopEntity(shop);
   }

   public void removeCashier(CashierEntity cashier) {
      cashiers.remove(cashier);
      cashier.setShopEntity(null);
   }

   public void addCashReceipt(CashReceiptEntity cashReceipt, ShopEntity shop) {
      cashReceipts.add(cashReceipt);
      cashReceipt.setShopEntity(shop);
   }

   public void removeCashReceipt(CashReceiptEntity cashReceipt) {
      cashReceipt.setShopEntity(null);
      this.cashReceipts.remove(cashReceipt);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      ShopEntity that = (ShopEntity) o;

      if (shopId != that.shopId)
         return false;
      if (name != null ? !name.equals(that.name) : that.name != null)
         return false;
      if (address != null ?
            !address.equals(that.address) :
            that.address != null)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = shopId;
      result = 31 * result + (name != null ? name.hashCode() : 0);
      result = 31 * result + (address != null ? address.hashCode() : 0);
      return result;
   }
}
