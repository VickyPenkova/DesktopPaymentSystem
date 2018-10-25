package jpaClasses;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CashReceipt", schema = "dbo", catalog = "DesktopPaymentSystem")
public class CashReceiptEntity {
    private int cashReceiptId;
    private int sameProductAmount;
    private CashierEntity cashierByCashierId;

    @Id
    @Column(name = "cashReceiptId")
    public int getCashReceiptId() {
        return cashReceiptId;
    }

    public void setCashReceiptId(int cashReceiptId) {
        this.cashReceiptId = cashReceiptId;
    }

    @Basic
    @Column(name = "sameProductAmount")
    public int getSameProductAmount() {
        return sameProductAmount;
    }

    public void setSameProductAmount(int sameProductAmount) {
        this.sameProductAmount = sameProductAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashReceiptEntity that = (CashReceiptEntity) o;
        return cashReceiptId == that.cashReceiptId &&
                sameProductAmount == that.sameProductAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cashReceiptId, sameProductAmount);
    }

    @ManyToOne
    @JoinColumn(name = "cashierId", referencedColumnName = "cashierId", nullable = false)
    public CashierEntity getCashierByCashierId() {
        return cashierByCashierId;
    }

    public void setCashierByCashierId(CashierEntity cashierByCashierId) {
        this.cashierByCashierId = cashierByCashierId;
    }
}
