package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CashReceipt", schema = "main")
public class CashReceiptEntity implements Serializable {
    private Object cashReceiptId;
    private Object cashierId;
    private Object productsAmount;
    private CashierEntity cashierByCashierId;

    @Basic
    @Column(name = "cashReceiptId")
    public Object getCashReceiptId() {
        return cashReceiptId;
    }

    public void setCashReceiptId(Object cashReceiptId) {
        this.cashReceiptId = cashReceiptId;
    }

    @Id
    @Column(name = "cashierId")
    public Object getCashierId() {
        return cashierId;
    }

    public void setCashierId(Object cashierId) {
        this.cashierId = cashierId;
    }

    @Basic
    @Column(name = "productsAmount")
    public Object getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(Object productsAmount) {
        this.productsAmount = productsAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashReceiptEntity that = (CashReceiptEntity) o;

        if (cashReceiptId != null ? !cashReceiptId.equals(that.cashReceiptId) : that.cashReceiptId != null)
            return false;
        if (cashierId != null ? !cashierId.equals(that.cashierId) : that.cashierId != null) return false;
        if (productsAmount != null ? !productsAmount.equals(that.productsAmount) : that.productsAmount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cashReceiptId != null ? cashReceiptId.hashCode() : 0;
        result = 31 * result + (cashierId != null ? cashierId.hashCode() : 0);
        result = 31 * result + (productsAmount != null ? productsAmount.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "cashierId", referencedColumnName = "cashierId", nullable = false)
    public CashierEntity getCashierByCashierId() {
        return cashierByCashierId;
    }

    public void setCashierByCashierId(CashierEntity cashierByCashierId) {
        this.cashierByCashierId = cashierByCashierId;
    }
}
