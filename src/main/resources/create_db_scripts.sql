-- MySQL Language used
drop table CashReceipt;
CREATE TABLE CashReceipt
(
  cashReceiptId int NOT NULL AUTO_INCREMENT,
  cashierId int NOT NULL,
  shopId int NOT NULL,
  totalPrice double default '0',
  CONSTRAINT CashReceipt_Shop_shopId_fk FOREIGN KEY (shopId) REFERENCES Shop (shopId),
  CONSTRAINT CashReceipt_Cashier_cashierId_fk FOREIGN KEY (cashierId) REFERENCES Cashier (cashierId),
  CONSTRAINT PRIMARY KEY(cashReceiptId)
);

CREATE TABLE Product (
  productId int NOT NULL,
  name varchar(600) NOT NULL,
  price double NOT NULL,
  shopId int NOT NULL,
  amount int default '0',
  CONSTRAINT PRIMARY KEY(productId),
  CONSTRAINT Product_Shop_shopId_fk FOREIGN KEY (shopId) REFERENCES Shop (shopId)
);

CREATE TABLE Cashier (
  cashierId int NOT NULL AUTO_INCREMENT,
  firstName varchar(600) NOT NULL,
  lastName varchar(600) NOT NULL,
  shopId int NOT NULL,
  CONSTRAINT  PRIMARY KEY(cashierId),

  CONSTRAINT Cashier_Shop_shopId_fk FOREIGN KEY (shopId) REFERENCES Shop (shopId)

);

-- auto-generated definition
create table Shop
(
  shopId  int        not null AUTO_INCREMENT,
  name    varchar(600) not null,
  address text       not null,
  CONSTRAINT primary key(shopId)
);