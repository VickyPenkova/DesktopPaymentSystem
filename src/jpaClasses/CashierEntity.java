package jpaClasses;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Cashier", schema = "dbo", catalog = "DesktopPaymentSystem")
public class CashierEntity {
    private int cashierId;
    private String firstName;
    private String lastName;

    @Id
    @Column(name = "cashierId")
    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashierEntity that = (CashierEntity) o;
        return cashierId == that.cashierId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cashierId, firstName, lastName);
    }
}
