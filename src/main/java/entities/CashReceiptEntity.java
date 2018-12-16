package entities;

import javax.persistence.*;

@Entity
@Table(name = "CashReceipt", schema = "DesktopPaymentSystem", catalog = "")
public class CashReceiptEntity {
   private int cashReceiptId;
   private int productsAmount;

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
   @Column(name = "productsAmount", nullable = false)
   public int getProductsAmount() {
      return productsAmount;
   }

   public void setProductsAmount(int productsAmount) {
      this.productsAmount = productsAmount;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      CashReceiptEntity that = (CashReceiptEntity) o;

      if (cashReceiptId != that.cashReceiptId)
         return false;
      if (productsAmount != that.productsAmount)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = cashReceiptId;
      result = 31 * result + productsAmount;
      return result;
   }
}
