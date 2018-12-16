package entities;

import javax.persistence.*;

@Entity
@Table(name = "Cashier", schema = "DesktopPaymentSystem", catalog = "")
public class CashierEntity {
   private int cashierId;
   private String firstName;
   private String lastName;

   @Id
   @Column(name = "cashierId", nullable = false)
   public int getCashierId() {
      return cashierId;
   }

   public void setCashierId(int cashierId) {
      this.cashierId = cashierId;
   }

   @Basic
   @Column(name = "firstName", nullable = false, length = 600)
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   @Basic
   @Column(name = "lastName", nullable = false, length = 600)
   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      CashierEntity that = (CashierEntity) o;

      if (cashierId != that.cashierId)
         return false;
      if (firstName != null ?
            !firstName.equals(that.firstName) :
            that.firstName != null)
         return false;
      if (lastName != null ?
            !lastName.equals(that.lastName) :
            that.lastName != null)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = cashierId;
      result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
      result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
      return result;
   }
}
