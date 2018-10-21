CREATE TABLE [Shop] (
	shopId int NOT NULL,
	name nchar(600) NOT NULL,
	address text(2000) NOT NULL,
  CONSTRAINT [PK_SHOP] PRIMARY KEY CLUSTERED
  (
  [shopId] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Cashier] (
	cashierId int NOT NULL,
	firstName nchar(600) NOT NULL,
	lastName nchar(600) NOT NULL,
	shopId int NOT NULL,
  CONSTRAINT [PK_CASHIER] PRIMARY KEY CLUSTERED
  (
  [cashierId] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Product] (
	productId int NOT NULL,
	name nchar(600) NOT NULL,
	price money NOT NULL,
	shopId int NOT NULL
)
GO
CREATE TABLE [CashReceipt] (
	cashReceiptId int NOT NULL,
	productId int NOT NULL,
	cashierId int NOT NULL,
	productsAmount int NOT NULL,
	shopId int NOT NULL,
  CONSTRAINT [PK_CASHRECEIPT] PRIMARY KEY CLUSTERED
  (
  [cashReceiptId] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO

ALTER TABLE [Cashier] WITH CHECK ADD CONSTRAINT [Cashier_fk0] FOREIGN KEY ([shopId]) REFERENCES [Shop]([shopId])
ON UPDATE CASCADE
GO
ALTER TABLE [Cashier] CHECK CONSTRAINT [Cashier_fk0]
GO

ALTER TABLE [Product] WITH CHECK ADD CONSTRAINT [Product_fk0] FOREIGN KEY ([shopId]) REFERENCES [Shop]([shopId])
ON UPDATE CASCADE
GO
ALTER TABLE [Product] CHECK CONSTRAINT [Product_fk0]
GO

ALTER TABLE [CashReceipt] WITH CHECK ADD CONSTRAINT [CashReceipt_fk0] FOREIGN KEY ([productId]) REFERENCES [Product]([productId])
ON UPDATE CASCADE
GO
ALTER TABLE [CashReceipt] CHECK CONSTRAINT [CashReceipt_fk0]
GO
ALTER TABLE [CashReceipt] WITH CHECK ADD CONSTRAINT [CashReceipt_fk1] FOREIGN KEY ([cashierId]) REFERENCES [Cashier]([cashierId])
ON UPDATE CASCADE
GO
ALTER TABLE [CashReceipt] CHECK CONSTRAINT [CashReceipt_fk1]
GO
ALTER TABLE [CashReceipt] WITH CHECK ADD CONSTRAINT [CashReceipt_fk2] FOREIGN KEY ([shopId]) REFERENCES [Shop]([shopId])
ON UPDATE CASCADE
GO
ALTER TABLE [CashReceipt] CHECK CONSTRAINT [CashReceipt_fk2]
GO

