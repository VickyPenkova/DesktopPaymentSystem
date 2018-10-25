package jpaClasses;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Shop", schema = "dbo", catalog = "DesktopPaymentSystem")
public class ShopEntity {
    private int shopId;
    private String name;
    private String address;
    private CashierEntity cashierByCashierId;

    @Id
    @Column(name = "shopId")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopEntity that = (ShopEntity) o;
        return shopId == that.shopId &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId, name, address);
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
