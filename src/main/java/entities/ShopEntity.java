package entities;

import services.CashierService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Shop", schema = "DesktopPaymentSystem")
public class ShopEntity {
   protected int shopId;

   private String name;
   private String address;

   @OneToMany(
         cascade = CascadeType.ALL,
         orphanRemoval = true
   )
   private List<CashierEntity> cashiers = new ArrayList<>();

   @OneToMany(
         cascade = CascadeType.ALL,
         orphanRemoval = true
   )
   private List<ProductEntity> products = new ArrayList<>();

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

   public void addCashier(CashierEntity cashier) {
      cashiers.add(cashier);
      cashier.setShopEntity(this);
   }

   public void removeCashier(CashierEntity cashier) {
      cashiers.remove(cashier);
      cashier.setShopEntity(null);
   }

   public void addProduct(ProductEntity product) {
      products.add(product);
      product.setShopEntity(this);
   }

   public void removeProduct(ProductEntity product) {
      products.remove(product);
      product.setShopEntity(null);
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
