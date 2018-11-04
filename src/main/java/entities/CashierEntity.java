package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cashier", schema = "main")
public class CashierEntity implements Serializable {
    private Object cashierId;
    private Object firstName;
    private Object lastName;

    @Id
    @Column(name = "cashierId")
    public Object getCashierId() {
        return cashierId;
    }

    public void setCashierId(Object cashierId) {
        this.cashierId = cashierId;
    }

    @Basic
    @Column(name = "firstName")
    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashierEntity that = (CashierEntity) o;

        if (cashierId != null ? !cashierId.equals(that.cashierId) : that.cashierId != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cashierId != null ? cashierId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
